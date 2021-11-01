package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.dao.ShopDAO;
import com.ankit.ezymanage.model.Shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopDAO shopDAO;

    @Autowired
    public ShopServiceImpl(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
    }

    @Override
    public boolean createShop(Shop shop) {
        shopDAO.createShop(shop);
        return true;
    }

    @Override
    public List<Shop> getAllShops() {
        return shopDAO.getAllShops();
    }

    // TODO: Unimplemented
    @Override
    public List<Shop> getAllShopsUnder(String owner) {
        return shopDAO.getAllShops();
    }

    @Override
    public void addProductToShop(int shopId, int productId) {
        shopDAO.addProductToShop(shopId, productId);
    }

    @Override
    public Shop getShopById(int id) {
        return shopDAO.getShopById(id);
    }

    @Override
    public void removeProductFromShop(int shopId, int productId) {
        shopDAO.removeProductFromShop(shopId, productId);
    }

    @Override
    public void deleteShop(int id) {
        shopDAO.deleteShop(id);
    }
}
