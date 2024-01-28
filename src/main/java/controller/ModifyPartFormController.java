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

public class ModifyPartFormController implements Initializable {

    public RadioButton inHouse;
    public RadioButton Outsourced;
    public Label toggleText;
    public TextField toggleTF;
    public TextField partIdLbl;
    public TextField nameTF;
    public TextField invTF;
    public TextField priceTF;
    public TextField maxTF;
    public TextField minTF;
    Stage stage;
    Parent scene;

    /** This method opens the Main Menu if the Cancel button is clicked.
     @param actionEvent The Cancel button is clicked
     */
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
            String idS = partIdLbl.getText();
            String nameS = nameTF.getText();
            String invS = invTF.getText();
            String priceS = priceTF.getText();
            String maxS = maxTF.getText();
            String minS = minTF.getText();
            String toggleTFS = toggleTF.getText();

            if (nameS.isBlank()) {

                Alert nameBlank = new Alert(Alert.AlertType.ERROR);
                nameBlank.setTitle("Invalid Input");
                nameBlank.setContentText("Please enter a name!");
                nameBlank.showAndWait();

                return;
            }

            int id = Integer.parseInt(idS);
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

            int index = id - 1;

            if (inHouse.isSelected()) {
                int machineId = Integer.parseInt(toggleTFS);
                InHouse newPart = new InHouse(id, nameS, price, stock, min, max, machineId);
                Inventory.updatePart(index, newPart);
            } else {
                Outsourced newPart = new Outsourced(id, nameS, price, stock, min, max, toggleTFS);
                Inventory.updatePart(index, newPart);
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

    /** This method sends the selected part to the Modify Part Form.
     @param selectedPart The selected part
     */
    public void sendPart(Part selectedPart) {

        partIdLbl.setText(String.valueOf(selectedPart.getId()));
        nameTF.setText(String.valueOf(selectedPart.getName()));
        invTF.setText(String.valueOf(selectedPart.getStock()));
        priceTF.setText(String.valueOf(selectedPart.getPrice()));
        maxTF.setText(String.valueOf(selectedPart.getMax()));
        minTF.setText(String.valueOf(selectedPart.getMin()));

        if(selectedPart instanceof Outsourced) {
            toggleTF.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
            Outsourced.setSelected(true);
            toggleText.setText("Company Name");
        }
        else if(selectedPart instanceof InHouse) {
            toggleTF.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
            inHouse.setSelected(true);
            toggleText.setText("Machine ID");
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
