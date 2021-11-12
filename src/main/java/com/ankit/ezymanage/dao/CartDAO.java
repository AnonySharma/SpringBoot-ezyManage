package com.ankit.ezymanage.dao;

import java.sql.Timestamp;
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
        Timestamp currentTime = new java.sql.Timestamp(new java.util.Date().getTime());
        String sql = "INSERT INTO cart (id, shop_id, customer_id, total, date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cart.getId(), cart.getShopId(), cart.getCustomerId(), cart.getTotal(), currentTime);
    }

    public void updateCart(Cart cart) {
        String sql = "UPDATE cart SET shop_id = ?, date = ?, total = ? WHERE id = ?";
        jdbcTemplate.update(sql, cart.getShopId(), cart.getDate(), cart.getTotal(), cart.getId());
    }

    public List<Cart> getCartsByUserId(int userId) {
        String sql = "SELECT * FROM cart WHERE customer_id = ?";
        return jdbcTemplate.query(sql, RowMappers.cartRowMapper, userId);
    }

    public Cart getCartByUserIdAndShopID(int userId, int shopId) {
        String sql = "SELECT * FROM cart WHERE customer_id = ? AND shop_id = ?";
        Cart cart = jdbcTemplate.queryForObject(sql, RowMappers.cartRowMapper, userId, shopId);

        sql = "SELECT * FROM cart_items WHERE cart_id = ?";
        List<Pair<Integer, Integer>> products = jdbcTemplate.query(sql, RowMappers.cartItemRowMapper, cart.getId());
        cart.setProducts(products);
        return cart;
    }

    public void deleteCartByUserIdAndShopId(int userId, int shopId) {
        Cart cart = getCartByUserIdAndShopID(userId, shopId);
        if (cart != null)
            clearCart(cart.getId());

        String sql = "DELETE FROM cart WHERE customer_id = ? AND shop_id = ?";
        jdbcTemplate.update(sql, userId, shopId);
    }

    public boolean cartExistsForUserId(int userId, int shopId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE shop_id = ? AND customer_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, shopId, userId) != 0;
    }

    private boolean productExistsInCart(int cartId, int productId) {
        String sql = "SELECT COUNT(*) FROM cart_items WHERE cart_id = ? AND product_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cartId, productId) != 0;
    }

    private void addProductToCart(int cartId, int productId, int quantity) {
        String sql = "INSERT INTO cart_items (product_id, quantity, cart_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productId, quantity, cartId);
    }

    private void changeProductQuantityInCart(int cartId, int productId, int quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE cart_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, quantity, cartId, productId);
    }

    private void deleteProductFromCart(int cartId, int productId) {
        String sql = "DELETE FROM cart_items WHERE product_id = ? AND cart_id = ?";
        jdbcTemplate.update(sql, productId, cartId);
    }

    public void updateProductInCart(int cartId, int productId, int quantity) {
        if (productExistsInCart(cartId, productId)) {
            if (quantity == 0) {
                deleteProductFromCart(cartId, productId);
            } else {
                changeProductQuantityInCart(cartId, productId, quantity);
            }
        } else {
            if (quantity != 0) {
                addProductToCart(cartId, productId, quantity);
            }
        }
        updateCartTotal(cartId);
    }

    public int getProductQuantityInCart(int cartId, int productId) {
        String sql = "SELECT quantity FROM cart_items WHERE product_id = ? AND cart_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, productId, cartId);
    }

    public void clearCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartId);
        updateCartTotal(cartId);
    }

    private boolean isCartEmpty(int cartId) {
        String sql = "SELECT COUNT(*) FROM cart_items WHERE cart_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cartId) == 0;
    }

    public void updateCartTotal(int cartId) {
        if (isCartEmpty(cartId)) {
            String sql = "UPDATE cart SET total = 0 WHERE id = ?";
            jdbcTemplate.update(sql, cartId);
            return;
        }

        String sql = "SELECT SUM(cart_items.quantity * products.price) FROM cart_items INNER JOIN products ON cart_items.product_id = products.id WHERE cart_items.cart_id = ?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class, cartId);
        sql = "UPDATE cart SET total = ? WHERE id = ?";
        jdbcTemplate.update(sql, total, cartId);
    }

    public void deleteCartByCartId(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartId);
        sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }

}
