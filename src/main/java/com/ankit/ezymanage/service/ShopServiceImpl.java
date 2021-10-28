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
    public void createShop(Shop shop) {
        shopDAO.createShop(shop);
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
}
