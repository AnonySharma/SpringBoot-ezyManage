package com.ankit.ezymanage.model;

import java.util.List;

import com.ankit.ezymanage.utils.Pair;

public class Cart {
    private int id;
    private String date;
    private int shopId;
    private Integer customerId;
    private List<Pair<Integer, Integer>> products;
    private int total;

    public boolean checkProduct(int productId) {
        for (Pair<Integer, Integer> product : this.products) {
            if (product.getFirst() == productId) {
                return true;
            }
        }

        return false;
    }

    public void addProduct(int productId, int count) {
        this.products.add(new Pair<Integer, Integer>(productId, count));
    }

    public void removeProduct(int productId) {
        for (Pair<Integer, Integer> product : this.products) {
            if (product.getFirst() == productId) {
                this.products.remove(product);
                break;
            }
        }
    }

    public Cart() {
    }

    public Cart(int id, String date, int shopId, Integer customerId, List<Pair<Integer, Integer>> products, int total) {
        this.id = id;
        this.date = date;
        this.shopId = shopId;
        this.customerId = customerId;
        this.products = products;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<Pair<Integer, Integer>> getProducts() {
        return products;
    }

    public void setProducts(List<Pair<Integer, Integer>> products) {
        this.products = products;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Cart [customerId=" + customerId + ", date=" + date + ", id=" + id + ", products=" + products
                + ", shopId=" + shopId + ", total=" + total + "]";
    }

}
