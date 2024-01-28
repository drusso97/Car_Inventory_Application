package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * This class stores parts and products in observable lists and defines the functions to manage them.
 * */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds parts to the allParts observable list.
     * @param newPart New part object
     * */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds prducts to the allProducts observable list.
     * @param newProduct New part object
     * */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Lookup parts by their ID.
     * @param partId The part's id
     * @return Returns whatever parts are found and null if none are found
     * */
    public static Part lookupPart(int partId) {

        for(Part part : Inventory.getAllParts())
            while(part.getId() == partId) {
                return part;
            }
        Alert noPartFound = new Alert(Alert.AlertType.ERROR);
        noPartFound.setTitle("Error");
        noPartFound.setHeaderText("No part found");
        noPartFound.show();
        return null;
    }

    /**
     * Lookup products by their ID.
     * @param productId The product's id
     * @return Returns whatever parts are found and null if none are found
     * */
    public static Product lookupProduct(int productId) {

        for(Product product : Inventory.getAllProducts())
            while(product.getId() == productId) {
                return product;
            }
        Alert noProductFound = new Alert(Alert.AlertType.ERROR);
        noProductFound.setTitle("Error");
        noProductFound.setHeaderText("No product found");
        noProductFound.show();
        return null;
    }

    /**
     * Lookup parts by their name.
     * @param partName The search string
     * @return Returns whatever parts are found
     * */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for(Part part : allParts)
            if(part.getName().contains(partName)) {
                PartName.add(part);
            }
        return PartName;
    }

    /**
     * Lookup products by their name.
     * @param productName The search string
     * @return Returns whatever products are found
     * */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for(Product product : allProducts)
            if(product.getName().contains(productName))
                ProductName.add(product);

        return ProductName;

    }

    /**
     * Applies changes to parts.
     * @param index The part's index location in the observable list
     * @param selectedPart The new part object
     * */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);

    }

    /**
     * Applies changes to products.
     * @param index The product's index location in the observable list
     * @param newProduct The new product object
     * */
    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);

    }

    /**
     * Deletes a part from the observable list.
     * @param selectedPart The part to be deleted
     * */
    public boolean deletePart(Part selectedPart) {

        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Deletes a product from the observable list.
     * @param selectedProduct The product to be deleted
     * */
    public static boolean deleteProduct(Product selectedProduct) {

        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns all parts in the observable list.
     * @return allParts observable list
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns all products in the observable list.
     * @return allProducts observable list
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
