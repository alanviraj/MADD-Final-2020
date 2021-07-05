package com.example.vehiclebath;

import java.io.Serializable;

public class Product implements Serializable {

    private String id;
    private String name;
    private double price;
    private String description;
    private int photo;

    public Product(String id, String name, double price, String description, int photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
