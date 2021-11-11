package com.ankit.ezymanage.service;

import com.ankit.ezymanage.model.Cart;

public interface CartService {

    void addNewCart(Cart cart);

    void clearCart(int cartId);

    Cart getCartByUserIdAndShopId(int userId, int shopId);

    void deleteCartByUserIdAndShopId(int userId, int shopId);

    void deleteCartByCartId(int id);

    boolean cartExistsForUserId(int userId, int shopId);

    void updateProductInCart(int cartId, int productId, int quantity);

    void decrementProductInCart(int cartId, int productId);

    void incrementProductInCart(int cartId, int productId);

}
