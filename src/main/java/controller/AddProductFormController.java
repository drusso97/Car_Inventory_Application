package controller;

import javafx.collections.FXCollections;
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


public class AddProductFormController implements Initializable {

    public TableView allPartsTable;
    public TableColumn allPartsIdCol;
    public TableColumn allPartsNameCol;
    public TableColumn allPartsInvCol;
    public TableColumn allPartsCostCol;
    public TableView associatedPartsTable;
    public TableColumn associatedPartsIdCol;
    public TableColumn associatedPartsNameCol;
    public TableColumn associatedPartsInvCol;
    public TableColumn associatedPartsCostCol;
    public TextField idTF;
    public TextField nameTF;
    public TextField invTF;
    public TextField priceTF;
    public TextField maxTF;
    public TextField minTF;
    public TextField partSearchField;
    private ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;


    /** Returns to program's main menu.
     * @param event Cancel button is clicked
     * */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Adds a part to the product's associated parts.
     * @param actionEvent Add button is clicked
     * */
    public void onAddPart(ActionEvent actionEvent) {

        Part SP = (Part) allPartsTable.getSelectionModel().getSelectedItem();

        if(SP == null)
        {
            Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
            noPartSelected.setTitle("Error");
            noPartSelected.setContentText("Please select a part!");
            noPartSelected.showAndWait();
        }
        else if(!productAssociatedParts.contains(SP))
        {
            productAssociatedParts.add(SP);
            associatedPartsTable.setItems(productAssociatedParts);
        }
    }

    /** Removes a part from the product's associated parts.
     * @param actionEvent Remove button is clicked
     * */
    public void onRemovePart(ActionEvent actionEvent) {

        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("Confirm Delete Part");
        deleteConfirmation.setHeaderText("Delete");
        deleteConfirmation.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = deleteConfirmation.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            Part SP = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();

            if (SP == null) {
                Alert noPartSelected = new Alert(Alert.AlertType.ERROR);
                noPartSelected.setTitle("Error");
                noPartSelected.setContentText("Please select a part!");
                noPartSelected.showAndWait();
            } else if (productAssociatedParts.contains(SP)) {
                productAssociatedParts.remove(SP);
                associatedPartsTable.setItems(productAssociatedParts);
            }
        }
    }

    /** Saves changes to the product and returns to main menu.
     * @param actionEvent Save button is clicked
     * */
    public void onSave(ActionEvent actionEvent) throws IOException {

        try
        {
            int id = Inventory.getAllProducts().size() + 1; // id starts at one and increments by one when a new product is added

            // This for loop ensures that ids are never repeated by checking if the id is always in the list
            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() == id)
                    id++;
            }

            String nameS = nameTF.getText();
            String invS = invTF.getText();
            String priceS = priceTF.getText();
            String maxS = maxTF.getText();
            String minS = minTF.getText();

            int stock = Integer.parseInt(invS);
            double price = Double.parseDouble(priceS);
            int max = Integer.parseInt(maxS);
            int min = Integer.parseInt(minS);

            if (nameS.isBlank()) {

                Alert nameBlank = new Alert(Alert.AlertType.ERROR);
                nameBlank.setTitle("Invalid Input");
                nameBlank.setContentText("Please enter a name!");
                nameBlank.showAndWait();

                return;
            }

            if (min > max) {

                Alert minGreaterThanMax = new Alert(Alert.AlertType.ERROR);
                minGreaterThanMax.setTitle("Invalid Input");
                minGreaterThanMax.setContentText("Min must be less or equal to max!");
                minGreaterThanMax.showAndWait();

                return;

            } else if (stock > max) {

                Alert stockGreaterThanMax = new Alert(Alert.AlertType.ERROR);
                stockGreaterThanMax.setTitle("Invalid Input");
                stockGreaterThanMax.setContentText("Inv must be less or equal to max!");
                stockGreaterThanMax.showAndWait();

                return;

            } else if (stock < min) {

                Alert stockLessThanMin = new Alert(Alert.AlertType.ERROR);
                stockLessThanMin.setTitle("Invalid Input");
                stockLessThanMin.setContentText("Inv must be greater than or equal to min!");
                stockLessThanMin.showAndWait();

                return;
            }

            Product newProduct = new Product(id, nameS, price, stock, min, max);

            for (Part part : productAssociatedParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect Type");
            alert.setContentText("One or more fields has an incorrect value type or is blank!");
            alert.showAndWait();
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
            allPartsTable.setItems(searchResults);
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

        allPartsTable.setItems(Inventory.getAllParts());
        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(productAssociatedParts);
        associatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}
