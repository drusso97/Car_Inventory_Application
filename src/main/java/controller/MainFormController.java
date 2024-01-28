package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    public TableView partsTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partCostCol;
    public TableView productsTable;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productCostCol;
    public TextField productSearchField;
    public TextField partSearchField;
    Stage stage;
    Parent scene;

    /** This method closes the program. */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /** Loads the add part form.
     * @param event Add button clicked
     * */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Loads the modify part form for the selected part.
     * @param event Modify button clicked
     * */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartFormController MPFController = loader.getController();
            MPFController.sendPart((Part) partsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No part selected!");
            alert.setContentText("Please select a product to modify");
            alert.show();
        }
    }

    /** Deletes the selected part.
     * LOGIC ERROR
     * The program had a logic error where two products/parts could end up with the same ID if a product/part was deleted due to the way IDs are generated.
     * I decided that when a product/part was deleted to decrement each partID/productID with a higher ID by one to ensure IDs remain unique.
     * I also could have probably put any used IDs in an observable list and iterated through the list with a for loop to make sure the id had not been used already, but I decided this was simpler.
     * @param event Delete button clicked
     * */
    @FXML
    void onActionDeletePart(ActionEvent event) throws IOException {

        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("Confirm Delete Part");
        deleteConfirmation.setHeaderText("Delete");
        deleteConfirmation.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = deleteConfirmation.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            try {
                Part deletedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

                int id = deletedPart.getId();

                partsTable.getItems().removeAll(partsTable.getSelectionModel().getSelectedItem());

                for (Part part : Inventory.getAllParts())
                    if (part.getId() > id)
                        part.setId(part.getId() - 1);
            }
            catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: No part selected!");
                alert.setContentText("Please select a part to delete");
                alert.show();
            }
        }
    }

    /** Loads the add product form.
     * @param event Add button clicked
     * */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Loads the modify product form for the selected product.
     * @param event Modify button clicked
     * */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductFormController MPFController = loader.getController();
            MPFController.sendProduct((Product) productsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No product selected!");
            alert.setContentText("Please select a product to modify");
            alert.show();
        }

    }


    /** Deletes the selected product.
     * LOGIC ERROR
     * The program had a logic error where two products/parts could end up with the same ID if a product/part was deleted due to the way IDs are generated.
     * I decided that when a product/part was deleted to decrement each partID/productID with a higher ID by one to ensure IDs remain unique.
     * I also could have probably put any used IDs in an observable list and iterated through the list with a for loop to make sure the id had not been used already, but I decided this was simpler.
     * @param event Delete button clicked
     * */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException {

        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("Confirm Delete Product");
        deleteConfirmation.setHeaderText("Delete");
        deleteConfirmation.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = deleteConfirmation.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            try {
                Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

                int id = selectedProduct.getId();

                if (selectedProduct.getAllAssociatedParts().size() > 0) {
                    Alert deleteError = new Alert(Alert.AlertType.ERROR);
                    deleteError.setTitle("Cannot delete product!");
                    deleteError.setContentText("Product has associated parts and cannot be deleted until associated parts are removed!");
                    deleteError.showAndWait();
                    return;
                } else
                    productsTable.getItems().removeAll(productsTable.getSelectionModel().getSelectedItem());

                // makes sure that Ids are never duplicated by decrementing each higher product id by one when a part is deleted. This is necessary due to the way ids are generated
                for (Product product : Inventory.getAllProducts())
                    if (product.getId() > id)
                        product.setId(product.getId() - 1);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: No product selected!");
                alert.setContentText("Please select a product to delete");
                alert.show();
            }
        }
    }

    /** Searches for products by a given integer or string.
     * @param event Text is entered in search bar
     * */
    public void onProductSearch(KeyEvent event) {

        String searchString = productSearchField.getText();
        ObservableList<Product> searchResults = Inventory.lookupProduct(searchString);

        try
        {
            while (searchResults.isEmpty()) {
                int searchId = Integer.parseInt(searchString);
                searchResults.add(Inventory.lookupProduct(searchId));
            }
            productsTable.setItems(searchResults);
        }
        catch (NumberFormatException exception) {
            Alert noProductsFound = new Alert(Alert.AlertType.ERROR);
            noProductsFound.setTitle("Error");
            noProductsFound.setContentText("No products found!");
            noProductsFound.showAndWait();
        }

    }

    /** Searches for parts by a given integer or string.
     * @param event Text is entered in search bar
     * */
    public void onPartSearch(KeyEvent event) {

        String searchString = partSearchField.getText();
        ObservableList<Part> searchResults = Inventory.lookupPart(searchString);

        try
        {
            while (searchResults.isEmpty()) {
                int searchId = Integer.parseInt(searchString);
                searchResults.add(Inventory.lookupPart(searchId));
            }
            partsTable.setItems(searchResults);
        }
        catch (NumberFormatException exception) {
            Alert noPartsFound = new Alert(Alert.AlertType.ERROR);
            noPartsFound.setTitle("Error");
            noPartsFound.setContentText("No parts found!");
            noPartsFound.showAndWait();
        }

    }

    /** Initialize and add data to tables.
     * @param url URL
     * @param resourceBundle ResourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productsTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}