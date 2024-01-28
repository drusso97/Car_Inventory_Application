package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class defines product objects.
 * */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the product's id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the product's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the product's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the product's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the product's stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the product's stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the product's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the product's price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the minimum stock
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the minimum stock
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the maximum stock
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the maximum stock
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the associated parts list
     * @param part The part to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from the associated parts list
     * @param selectedAssociatedPart The part to be deleted
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * Returns all associated parts for a given product
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
