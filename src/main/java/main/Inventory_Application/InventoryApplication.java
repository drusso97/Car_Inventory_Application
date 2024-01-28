package main.Inventory_Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.io.IOException;

/*
    JavaDoc located at Car_Inventory_Application/JavaDoc
 */

/** This class creates an inventory management application.
 * FUTURE ENHANCEMENT: I could store all the used IDs in an Observable List so that IDs are not reused and other IDs do not need to be changed when a part/product is deleted.
 * This would probably speed up the program too if it had hundreds or thousands of objects to sort through.
 * */
public class InventoryApplication extends Application {

    /** This is the start method. */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 995, 360);
        stage.setTitle("Inventory Application");
        stage.setScene(scene);
        stage.show();
    }

    /** This method adds test data. */
    private static void addTestData(){

        Product cruze = new Product(1, "Cruze", 24000, 12, 10, 20);
        Inventory.addProduct(cruze);

        Product civic = new Product(2, "Civic", 34000, 15, 10, 20);
        Inventory.addProduct(civic);

        Product jeep = new Product(3, "Jeep", 41000, 7, 3, 20);
        Inventory.addProduct(jeep);

        Product tesla = new Product(4, "Tesla", 75000, 5, 2, 17);
        Inventory.addProduct(tesla);

        Product f150 = new Product(5, "F150", 40000, 20, 10, 50);
        Inventory.addProduct(f150);

        Product trailblazer = new Product(6, "Trail Blazer", 40000, 7, 5, 20);
        Inventory.addProduct(trailblazer);

        Outsourced sw = new Outsourced(1, "Steering Wheel", 500.00, 15, 10, 20, "Honda");
        Inventory.addPart(sw);

        InHouse tire = new InHouse(2, "Tire", 220, 15, 10, 20, 100);
        Inventory.addPart(tire);

        Outsourced windshield = new Outsourced(3, "Windshield", 500.00, 15, 10, 20, "Chevrolet");
        Inventory.addPart(windshield);

        InHouse battery = new InHouse(4, "Battery", 150, 50, 10, 200, 84);
        Inventory.addPart(battery);

    }

    /** This is the main method. This is the first method that gets called when you run your java program. */
    public static void main(String[] args) {

        addTestData();

        launch();
    }
}