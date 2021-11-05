package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Shop;

public interface ShopService {
    boolean createShop(Shop shop);

    List<Shop> getAllShops();

    List<Shop> getAllShopsUnder(String ownerUsername);

    Shop getShopById(int id);

    List<Product> getProductsByShop(int shopId);

    void addProductToShop(int shopId, int productId);

    void removeProductFromShop(int shopId, int productId);

    void deleteShop(int id);
}
