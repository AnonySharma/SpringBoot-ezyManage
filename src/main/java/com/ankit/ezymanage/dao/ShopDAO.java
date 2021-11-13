package com.ankit.ezymanage.dao;

import java.util.List;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.model.Staff;
import com.ankit.ezymanage.utils.RowMappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDAO {
    private final JdbcTemplate jdbcTemplate;

    public ShopDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createShop(Shop shop) {
        final String sql = "INSERT INTO shops(id, name, type, phone, email, gstin, owner) VALUES(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, null, shop.getName(), shop.getType(), shop.getPhone(), shop.getEmail(),
                shop.getGSTIN(), shop.getOwner());
    }

    public Shop getShopById(int id) {
        final String sql = "SELECT * FROM shops WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, RowMappers.shopRowMapper, id);
    }

    public List<Shop> getAllShops() {
        final String sql = "SELECT * FROM shops";
        return jdbcTemplate.query(sql, RowMappers.shopRowMapper);
    }

    public List<Shop> getAllShopsUnder(String username) {
        final String sql = "SELECT * FROM shops where owner=?";
        return jdbcTemplate.query(sql, RowMappers.shopRowMapper, username);
    }

    public Shop getShop(int id) {
        final String sql = "SELECT * FROM shops WHERE id=?";
        return jdbcTemplate.queryForObject(sql, RowMappers.shopRowMapper, id);
    }

    public void updateShop(Shop shop) {
        final String sql = "UPDATE shops SET name=?, type=?, phone=?, email=?, gstin=?, owner=? WHERE id=?";
        jdbcTemplate.update(sql, shop.getName(), shop.getType(), shop.getPhone(), shop.getEmail(), shop.getGSTIN(),
                shop.getOwner(), shop.getId());
    }

    public void deleteShop(int id) {
        final String sql = "DELETE FROM shops WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public List<Product> getProductsByShop(int shopId) {
        String sql = "SELECT * FROM products WHERE id IN (SELECT product_id FROM shop_products WHERE shop_id = ?)";
        return jdbcTemplate.query(sql, RowMappers.productRowMapper, shopId);
    }

    public void addProductToShop(int shopId, int productId) {
        final String sql = "INSERT INTO shop_products(shop_id, product_id) VALUES(?, ?)";
        jdbcTemplate.update(sql, shopId, productId);
    }

    public void removeProductFromShop(int shopId, int productId) {
        final String sql = "DELETE FROM shop_products WHERE shop_id=? AND product_id=?";
        jdbcTemplate.update(sql, shopId, productId);
    }

    public void addStaff(Staff staff) {
        final String sql = "INSERT INTO shop_staffs(shop_id, staff_id, staff_name, date_of_joining, designation, salary) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, staff.getShopId(), staff.getStaffId(), staff.getName(), staff.getDateOfJoining(),
                staff.getDesignation(), staff.getSalary());
    }

    public void removeStaff(int shopId, int staffId) {
        final String sql = "DELETE FROM shop_staffs WHERE shop_id=? AND staff_id=?";
        jdbcTemplate.update(sql, shopId, staffId);
    }

    public List<Staff> getStaffsByShop(int shopId) {
        String sql = "SELECT * FROM shop_staffs WHERE shop_id = ?";
        return jdbcTemplate.query(sql, RowMappers.staffRowMapper, shopId);
    }

    public boolean checkStaffByShop(int shopId, int staffId) {
        String sql = "SELECT COUNT(*) FROM shop_staffs WHERE shop_id = ? AND staff_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, shopId, staffId) == 1;
    }

    public boolean checkIfStaffExists(int staffId) {
        String sql = "SELECT COUNT(*) FROM shop_staffs WHERE staff_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, staffId) > 0;
    }

}
