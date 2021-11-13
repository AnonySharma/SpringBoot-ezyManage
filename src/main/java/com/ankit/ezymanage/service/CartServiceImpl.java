package com.ankit.ezymanage.service;

import java.text.ParseException;

// import java.util.Calendar;
// import java.util.Date;

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
    public void addNewCart(Cart cart) throws ParseException {
        // System.out.println(cart);
        // Calendar today = Calendar.getInstance();
        // today.set(Calendar.HOUR_OF_DAY, 0);
        // cart.setDate(new Date(today.getTime().getTime()));
        // System.out.println(cart);
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
    public void deleteCartByUserIdAndShopId(int userId, int shopId) {
        cartDAO.deleteCartByUserIdAndShopId(userId, shopId);
    }

    @Override
    public void deleteCartByCartId(int id) {
        cartDAO.deleteCartByCartId(id);
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
