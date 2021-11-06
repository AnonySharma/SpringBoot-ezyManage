package com.ankit.ezymanage.service;

import com.ankit.ezymanage.model.Cart;

public interface CartService {

    void addNewCart(Cart cart);

    Cart getCartByUserId(int userId);

    boolean cartExistsForUserId(int shopId, int userId);

    void updateProductInCart(int cartId, int productId, int quantity);

    void decrementProductInCart(int cartId, int productId);

    void incrementProductInCart(int cartId, int productId);

    // int getQuantityOfProductInCart(int cartId, int productId);

    // public void addToCart(int userId, int productId);

    // public void removeFromCart(int userId, int productId);

    // public void updateCart(int userId, int productId, int quantity);

    // public void clearCart(int userId);

}
