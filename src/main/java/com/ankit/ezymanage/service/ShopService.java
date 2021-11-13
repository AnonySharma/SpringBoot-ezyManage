package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.model.Staff;

public interface ShopService {
    boolean createShop(Shop shop);

    void deleteShop(int id);

    List<Shop> getAllShops();

    List<Shop> getAllShopsUnder(String ownerUsername);

    Shop getShopById(int id);

    List<Product> getProductsByShop(int shopId);

    void addProductToShop(int shopId, int productId);

    void removeProductFromShop(int shopId, int productId);

    void addStaff(Staff staff);

    void removeStaff(int shopId, int staffId);

    List<Staff> getStaffsByShop(int shopId);

    boolean checkStaffByShop(int shopId, int staffId);

    boolean checkIfStaffExists(int staffId);

}
