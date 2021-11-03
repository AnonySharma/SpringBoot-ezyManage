package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Shop;

public interface ShopService {
    public boolean createShop(Shop shop);

    public List<Shop> getAllShops();

    public List<Shop> getAllShopsUnder(String ownerUsername);

    public Shop getShopById(int id);

    public List<Product> getProductsByShop(int shopId);

    public void addProductToShop(int shopId, int productId);

    public void removeProductFromShop(int shopId, int productId);

    public void deleteShop(int id);
}
