package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Shop;

public interface ShopService {
    public boolean createShop(Shop shop);

    public List<Shop> getAllShops();

    public List<Shop> getAllShopsUnder(String ownerUsername);

    public Shop getShopById(int id);

    public void addProductToShop(int shopId, int productId);

    public void removeProductFromShop(int shopId, int productId);

    public void deleteShop(int id);
}
