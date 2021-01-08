package com.zo.customerapplication;

public class ProductData {
    private int id;
    private String name;
    private String manufacturer;
    private int amount;
    private int shelf;

    public ProductData(int id, String name, String manufacturer, int amount, int shelf) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.amount = amount;
        this.shelf = shelf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }
}
