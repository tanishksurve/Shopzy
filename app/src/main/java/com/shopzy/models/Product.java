package com.shopzy.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private String description;
    private int imageResId;
    private String category;
    private float rating;

    public Product(String id, String name, double price, String description, int imageResId, String category, float rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResId = imageResId;
        this.category = category;
        this.rating = rating;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
    public float getRating() { return rating; }
}
