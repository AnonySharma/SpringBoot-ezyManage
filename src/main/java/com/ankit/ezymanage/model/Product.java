package com.ankit.ezymanage.model;

public class Product {
    private int productId;
    private String name;
    private String details;
    private int price;
    // private int brandId;

    public Product() {
    }

    public Product(int productId, String name, String details, int price) {
        this.productId = productId;
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
