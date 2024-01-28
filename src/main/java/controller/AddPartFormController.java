package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartFormController implements Initializable {

    public Label toggleText;
    public RadioButton inHouse;
    public RadioButton outsourced;
    public TextField partNameTF;
    public TextField partStockTF;
    public TextField partCostTF;
    public TextField partMaxTF;
    public TextField partMinTF;
    public TextField toggleTF;
    Stage stage;
    Parent scene;

    /** Returns to program's main menu.
     * @param actionEvent Cancel button is clicked
     * */
    @FXML
    void onActionDisplayMainMenu(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method sets the text label if the inHouse Radio Button is selected.
     @param actionEvent The inHouse button is selected
     */
    public void onInHouse(ActionEvent actionEvent) {
        toggleText.setText("Machine ID");
    }

    /** This method sets the text label if the outSourced Radio Button is selected.
     @param actionEvent The outSourced button is selected
     */
    public void onOutsourced(ActionEvent actionEvent) {
        toggleText.setText("Company Name");
    }

    /** This method saves the part and returns to the Main Menu when Save is clicked.
     @param actionEvent The Save button is clicked
     */
    @FXML
    void onActionSavePart(ActionEvent actionEvent) throws IOException {

        try {
            int id = Inventory.getAllParts().size() + 1; // id starts at one and increments by one when a new product is added

            // This for loop ensures that ids are never repeated by checking if the id is always in the list
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() == id)
                    id++;
            }

            String nameS = partNameTF.getText();
            String invS = partStockTF.getText();
            String priceS = partCostTF.getText();
            String maxS = partMaxTF.getText();
            String minS = partMinTF.getText();
            String toggleTFS = toggleTF.getText();

            if (nameS.isBlank()) {

                Alert nameBlank = new Alert(Alert.AlertType.ERROR);
                nameBlank.setTitle("Invalid Input");
                nameBlank.setContentText("Please enter a name!");
                nameBlank.showAndWait();

                return;
            }

            int stock = Integer.parseInt(invS);
            double price = Double.parseDouble(priceS);
            int max = Integer.parseInt(maxS);
            int min = Integer.parseInt(minS);

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


            if (inHouse.isSelected()) {
                int machineId = Integer.parseInt(toggleTFS);
                InHouse newInHouse = new InHouse(id, nameS, price, stock, min, max, machineId);
                Inventory.addPart(newInHouse);
            } else {
                Outsourced newOutsourced = new Outsourced(id, nameS, price, stock, min, max, toggleTFS);
                Inventory.addPart(newOutsourced);
            }

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
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

    /** Initialize.
     * @param url URL
     * @param resourceBundle ResourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
