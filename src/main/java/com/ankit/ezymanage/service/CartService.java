package com.ankit.ezymanage.service;

import com.ankit.ezymanage.model.Cart;

public interface CartService {

    void addNewCart(Cart cart);

    Cart getCartByUserId(int userId);

    boolean cartExistsForUserId(int shopId, int userId);

    // public void addToCart(int userId, int productId);

    // public void removeFromCart(int userId, int productId);

    // public void updateCart(int userId, int productId, int quantity);

    // public void clearCart(int userId);

}
