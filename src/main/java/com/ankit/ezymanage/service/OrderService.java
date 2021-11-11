package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.model.Order;

public interface OrderService {

    boolean checkoutCart(Cart cart, int staffId);

    List<Order> getAllOrders();

    List<Order> getOrdersByShopId(int shopId);

    Order getOrderByOrderId(int orderId);
}
