package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Shop;

public interface ShopService {
    public void createShop(Shop shop);

    public List<Shop> getAllShops();

    public List<Shop> getAllShopsUnder(String owner);
}
