package com.ankit.ezymanage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ankit.ezymanage.model.Shop;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        return jdbcTemplate.queryForObject(sql, shopRowMapper, id);
    }

    public List<Shop> getAllShops() {
        final String sql = "SELECT * FROM shops";
        return jdbcTemplate.query(sql, shopRowMapper);
    }

    public List<Shop> getAllShopsUnder(String username) {
        final String sql = "SELECT * FROM shops where owner=?";
        return jdbcTemplate.query(sql, shopRowMapper, username);
    }

    public Shop getShop(int id) {
        final String sql = "SELECT * FROM shops WHERE id=?";
        return jdbcTemplate.queryForObject(sql, shopRowMapper, id);
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

    public void addProductToShop(int shopId, int productId) {
        final String sql = "INSERT INTO shop_products(shop_id, product_id) VALUES(?, ?)";
        jdbcTemplate.update(sql, shopId, productId);
    }

    public void removeProductFromShop(int shopId, int productId) {
        final String sql = "DELETE FROM shop_products WHERE shop_id=? AND product_id=?";
        jdbcTemplate.update(sql, shopId, productId);
    }

    // TODO: Fix phone
    private RowMapper<Shop> shopRowMapper = new RowMapper<Shop>() {
        @Override
        public Shop mapRow(ResultSet row, int i) throws SQLException {
            Shop shop = new Shop();
            shop.setId(row.getInt("id"));
            shop.setName(row.getString("name"));
            shop.setType(row.getString("type"));
            shop.setPhone(null);
            shop.setEmail(row.getString("email"));
            shop.setGSTIN(row.getString("gstin"));
            shop.setOwner(row.getString("owner"));
            return shop;
        }
    };

}
