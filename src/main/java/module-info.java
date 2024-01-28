module devon.cs482_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.Inventory_Application to javafx.fxml;
    exports main.Inventory_Application;
    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.base;
}