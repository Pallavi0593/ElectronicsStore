package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.AddItemToCartRequest;
import com.Bikkadit.ElectronicsStore.dtos.CartDto;

public interface CartService {

    //Add Item To cart
    //Case !:Cart not available For particular User.We will Create Cart then Add item
    //CAse 2:Cart Available: Add Item To Cart

     CartDto addItemToCart(String userId, AddItemToCartRequest request);

     //Remove Item From Cart

     void removeItemFromCart(String userId,Integer cartItems);

     //Remove All Items From Cart
     void clearCart(String userId);


}
