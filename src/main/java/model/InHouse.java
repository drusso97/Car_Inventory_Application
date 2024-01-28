package model;

/**
 * Class defines inHouse parts.
 * */
public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id,name, price, stock, min, max);
        this.machineId = machineId;

    }

    /**
     * Gets machine ID.
     * */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets machine ID.
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
