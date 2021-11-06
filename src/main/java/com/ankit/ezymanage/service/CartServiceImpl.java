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
    public void clearCart(int cartId) {
        cartDAO.clearCart(cartId);
    }

    @Override
    public boolean cartExistsForUserId(int userId, int shopId) {
        return cartDAO.cartExistsForUserId(userId, shopId);
    }

    @Override
    public Cart getCartByUserIdAndShopId(int userId, int shopId) {
        return cartDAO.getCartByUserIdAndShopID(userId, shopId);
    }

    @Override
    public void updateProductInCart(int cartId, int productId, int quantity) {
        cartDAO.updateProductInCart(cartId, productId, quantity);
    }

    @Override
    public void decrementProductInCart(int cartId, int productId) {
        int quantity = cartDAO.getProductQuantityInCart(cartId, productId);
        cartDAO.updateProductInCart(cartId, productId, quantity - 1);
    }

    @Override
    public void incrementProductInCart(int cartId, int productId) {
        int quantity = cartDAO.getProductQuantityInCart(cartId, productId);
        cartDAO.updateProductInCart(cartId, productId, quantity + 1);
    }

}
