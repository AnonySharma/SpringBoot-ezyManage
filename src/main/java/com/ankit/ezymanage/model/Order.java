package com.ankit.ezymanage.model;

import java.util.List;

import com.ankit.ezymanage.utils.Pair;

public class Order {
    private int orderId;
    private int shopId;
    private int staffId;
    private int customerId;
    private String date;
    private List<Pair<Integer, Integer>> items;
    private int total;
    private String mode;
    private String status;

    public void addItems(int product_id, int quantity) {
        items.add(new Pair<Integer, Integer>(product_id, quantity));
    }

    public Order() {
    }

    public Order(int orderId, int shopId, int staffId, int customerId, String date, List<Pair<Integer, Integer>> items,
            int total, String mode, String status) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.customerId = customerId;
        this.date = date;
        this.items = items;
        this.total = total;
        this.mode = mode;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Pair<Integer, Integer>> getItems() {
        return items;
    }

    public void setItems(List<Pair<Integer, Integer>> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order [total=" + total + ", customerId=" + customerId + ", date=" + date + ", items=" + items
                + ", mode=" + mode + ", orderId=" + orderId + ", shopId=" + shopId + ", staffId=" + staffId
                + ", status=" + status + "]";
    }

}
