package com.ankit.ezymanage.dao;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.utils.RowMappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAO {
    private final JdbcTemplate jdbcTemplate;

    public CartDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createCart(Cart cart) {
        String sql = "INSERT INTO cart (id, shop_id, customer_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, cart.getId(), cart.getShopId(), cart.getCustomerId());
    }

    public void updateCart(Cart cart) {
        String sql = "UPDATE cart SET shop_id = ?, date = ?, total = ? WHERE id = ?";
        jdbcTemplate.update(sql, cart.getShopId(), cart.getDate(), cart.getTotal(), cart.getId());
    }

    public void deleteCart(Cart cart) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cart.getId());
    }

    public Cart getCartByUserId(int userId) {
        String sql = "SELECT * FROM cart WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, RowMappers.cartRowMapper, userId);
    }

    public void deleteCartByUserId(int userId) {
        String sql = "DELETE FROM cart WHERE customer_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public boolean cartExistsForUserId(int shopId, int userId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE shop_id = ? AND customer_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, shopId, userId) != 0;
    }
}
