package com.ankit.ezymanage.service;

import com.ankit.ezymanage.dao.CartDAO;
import com.ankit.ezymanage.model.Cart;

import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private final CartDAO cartDAO;

    public CartServiceImpl(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public void addNewCart(Cart cart) {
        cartDAO.createCart(cart);
    }

    @Override
    public boolean cartExistsForUserId(int shopId, int userId) {
        return cartDAO.cartExistsForUserId(shopId, userId);
    }

    @Override
    public Cart getCartByUserId(int userId) {
        return cartDAO.getCartByUserId(userId);
    }

}
