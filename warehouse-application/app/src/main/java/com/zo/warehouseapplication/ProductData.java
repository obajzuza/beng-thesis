package com.zo.warehouseapplication;

public class ProductData {
    private String name;
    private String manufacturer;
    private int id;
    private int amount;
    private boolean existsInShelf = false;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isExistsInShelf() {
        return existsInShelf;
    }

    public void setExistsInShelf(boolean existsInShelf) {
        this.existsInShelf = existsInShelf;
    }
}
