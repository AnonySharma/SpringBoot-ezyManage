package com.ankit.ezymanage.service;

import java.text.ParseException;
import java.util.List;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.model.Order;

public interface OrderService {

    boolean checkoutCart(Cart cart, int staffId, String mode) throws ParseException;

    List<Order> getAllOrders();

    List<Order> getOrdersByShopId(int shopId);

    Order getOrderByOrderId(int orderId);

    List<Order> getAllOrdersByUserId(int id);

    List<Order> getOrdersByCustomer(int shopId, int customerId);

    List<Order> getOrdersByStaff(int shopId, int staffId);
}
