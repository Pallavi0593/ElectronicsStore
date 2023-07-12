package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.CartService;
import com.Bikkadit.ElectronicsStore.dtos.AddItemToCartRequest;
import com.Bikkadit.ElectronicsStore.dtos.CartDto;

public class CartServiceImpl implements CartService {
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItems) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
