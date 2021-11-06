package com.ankit.ezymanage.dao;

import java.util.List;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.utils.Pair;
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
        Cart cart = jdbcTemplate.queryForObject(sql, RowMappers.cartRowMapper, userId);

        sql = "SELECT * FROM cart_items WHERE cart_id = ?";
        List<Pair<Integer, Integer>> products = jdbcTemplate.query(sql, RowMappers.cartItemRowMapper, cart.getId());
        cart.setProducts(products);
        return cart;
    }

    public void deleteCartByUserId(int userId) {
        String sql = "DELETE FROM cart WHERE customer_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public boolean cartExistsForUserId(int shopId, int userId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE shop_id = ? AND customer_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, shopId, userId) != 0;
    }

    public boolean productExistsInCart(int cartId, int productId) {
        String sql = "SELECT COUNT(*) FROM cart_items WHERE cart_id = ? AND product_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cartId, productId) != 0;
    }

    public void addProductToCart(int cartId, int productId, int quantity) {
        String sql = "INSERT INTO cart_items (product_id, quantity, cart_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productId, quantity, cartId);
    }

    public void updateProductInCart(int cartId, int productId, int quantity) {
        if (productExistsInCart(cartId, productId)) {
            if (quantity == 0) {
                deleteProductFromCart(cartId, productId);
            } else {
                String sql = "UPDATE cart_items SET quantity = ? WHERE cart_id = ? AND product_id = ?";
                jdbcTemplate.update(sql, quantity, cartId, productId);
            }
        } else {
            if (quantity != 0) {
                addProductToCart(cartId, productId, quantity);
            }
        }
    }

    public void deleteProductFromCart(int cartId, int productId) {
        String sql = "DELETE FROM cart_items WHERE product_id = ? AND cart_id = ?";
        jdbcTemplate.update(sql, productId, cartId);
    }

    public int getProductQuantityInCart(int cartId, int productId) {
        String sql = "SELECT quantity FROM cart_items WHERE product_id = ? AND cart_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, productId, cartId);
    }

}
