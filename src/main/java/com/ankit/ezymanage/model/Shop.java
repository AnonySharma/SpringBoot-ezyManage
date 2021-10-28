package com.ankit.ezymanage.model;

public class Shop {
    private int shopId;
    private String name;
    private String owner;
    private String type;
    private Long phone;
    private String email;
    private String GSTIN;

    public Shop() {
    }

    public Shop(int shopId, String name, String owner, String type, Long phone, String email, String gSTIN) {
        this.shopId = shopId;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.phone = phone;
        this.email = email;
        GSTIN = gSTIN;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGSTIN() {
        return GSTIN;
    }

    public void setGSTIN(String gSTIN) {
        GSTIN = gSTIN;
    }

    @Override
    public String toString() {
        return "Shop [GSTIN=" + GSTIN + ", email=" + email + ", name=" + name + ", owner=" + owner + ", phone=" + phone
                + ", shopId=" + shopId + ", type=" + type + "]";
    }

}
