package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.dao.OrderDAO;
import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Order createOrder(Cart cart, int staffId, String status, String mode) {
        Order order = new Order();
        order.setShopId(cart.getShopId());
        order.setStaffId(staffId);
        order.setCustomerId(cart.getCustomerId());
        order.setDate(cart.getDate());
        order.setItems(cart.getProducts());
        order.setTotal(cart.getTotal());
        order.setMode(mode);
        order.setStatus(status);
        return order;
    }

    @Override
    public boolean checkoutCart(Cart cart, int staffId) {
        Order order = createOrder(cart, staffId, "pending", "cash");
        orderDAO.insertOrder(order);
        return true;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    @Override
    public List<Order> getOrdersByShopId(int shopId) {
        return orderDAO.getOrdersByShopId(shopId);
    }

    @Override
    public Order getOrderByOrderId(int orderId) {
        return orderDAO.getOrderByOrderId(orderId);
    }

}
