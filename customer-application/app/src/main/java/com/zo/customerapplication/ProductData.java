package com.zo.customerapplication;

import java.util.ArrayList;
import java.util.List;

public class ProductData {
    private int id;
    private String name;
    private String manufacturer;
    private int amount;
    private List<Integer> shelves;

    public ProductData(int id, String name, String manufacturer, int amount, ArrayList<Integer> shelves) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.amount = amount;
        this.shelves = shelves;
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

    public List<Integer> getShelves() {
        return shelves;
    }

    public void setShelves(List<Integer> shelves) {
        this.shelves = shelves;
    }
}
