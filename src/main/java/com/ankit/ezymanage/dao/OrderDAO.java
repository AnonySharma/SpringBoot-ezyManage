package com.ankit.ezymanage.dao;

import java.util.List;

import com.ankit.ezymanage.model.Order;
import com.ankit.ezymanage.utils.Pair;
import com.ankit.ezymanage.utils.RowMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertOrder(Order order) {
        String sql = "INSERT INTO orders (order_id, token, shop_id, staff_id, customer_id, order_date, mode, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getOrderId(), order.getToken(), order.getShopId(), order.getStaffId(),
                order.getCustomerId(), order.getDate(), order.getMode(), order.getStatus());

        int orderId = getOrderByToken(order.getToken()).getOrderId();
        sql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        for (Pair<Integer, Integer> item : order.getItems()) {
            jdbcTemplate.update(sql, orderId, item.getFirst(), item.getSecond());
        }
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);

        sql = "DELETE FROM order_items WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }

    public Order getOrderByOrderId(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        Order order = jdbcTemplate.queryForObject(sql, RowMappers.orderRowMapper, orderId);

        List<Pair<Integer, Integer>> items = getOrderItemsByOrderId(orderId);
        order.setItems(items);
        return order;
    }

    public Order getOrderByToken(String token) {
        String sql = "SELECT * FROM orders WHERE token = ?";
        Order order = jdbcTemplate.queryForObject(sql, RowMappers.orderRowMapper, token);

        List<Pair<Integer, Integer>> items = getOrderItemsByOrderId(order.getOrderId());
        order.setItems(items);
        return order;
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        String sql = "SELECT * FROM orders WHERE customer_id = ?";
        List<Order> orders = jdbcTemplate.query(sql, RowMappers.orderRowMapper, customerId);

        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setItems(getOrderItemsByOrderId(orders.get(i).getOrderId()));
        }

        return orders;
    }

    public List<Order> getOrdersByShopId(int shopId) {
        String sql = "SELECT * FROM orders WHERE shop_id = ?";
        List<Order> orders = jdbcTemplate.query(sql, RowMappers.orderRowMapper, shopId);

        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setItems(getOrderItemsByOrderId(orders.get(i).getOrderId()));
        }

        return orders;
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = jdbcTemplate.query(sql, RowMappers.orderRowMapper);
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setItems(getOrderItemsByOrderId(orders.get(i).getOrderId()));
        }
        return orders;
    }

    public void addOrderItem(int orderId, int productId, int quantity) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, orderId, productId, quantity);

    }

    public List<Pair<Integer, Integer>> getOrderItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        return jdbcTemplate.query(sql, RowMappers.orderItemRowMapper, orderId);
    }

    // public void updateTotalPrice(int orderId) {
    // List<Pair<Integer, Integer>> orders = getOrderItemsByOrderId(orderId);

    // }
}