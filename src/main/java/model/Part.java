package model;

/**
 * Class defines part objects.
 * */
public abstract class Part{

    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;

    protected Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the part's id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the part's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the part's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the part's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the part's stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the part's stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the part's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the part's price
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
}


