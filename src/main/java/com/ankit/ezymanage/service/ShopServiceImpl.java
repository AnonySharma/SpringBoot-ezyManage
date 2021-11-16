package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.dao.ShopDAO;
import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.model.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopDAO shopDAO;
    private final UserService userService;

    @Autowired
    public ShopServiceImpl(ShopDAO shopDAO, UserService userService) {
        this.shopDAO = shopDAO;
        this.userService = userService;
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

    @Override
    public List<Shop> getAllShopsUnder(String owner) {
        return shopDAO.getAllShopsUnder(owner);
    }

    @Override
    public List<Product> getProductsByShop(int shopId) {
        return shopDAO.getProductsByShop(shopId);
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

    @Override
    public List<Integer> getCustomersByShop(int shopId) {
        return shopDAO.getCustomersByShop(shopId);
    }

    @Override
    public void addStaff(Staff staff) {
        if (checkIfStaffExists(staff.getStaffId()) == false) {
            userService.addRoleToUser(userService.getUserById(staff.getStaffId()).getUsername(), "ROLE_STAFF");
        }
        shopDAO.addStaff(staff);
    }

    @Override
    public void removeStaff(int shopId, int staffId) {
        shopDAO.removeStaff(shopId, staffId);
        if (checkIfStaffExists(staffId) == false) {
            userService.removeRoleFromUser(userService.getUserById(staffId).getUsername(), "ROLE_STAFF");
        }
    }

    @Override
    public List<Staff> getStaffsByShop(int shopId) {
        return shopDAO.getStaffsByShop(shopId);
    }

    @Override
    public boolean checkStaffByShop(int shopId, int staffId) {
        return shopDAO.checkStaffByShop(shopId, staffId);
    }

    @Override
    public boolean checkIfStaffExists(int staffId) {
        return shopDAO.checkIfStaffExists(staffId);
    }

}
