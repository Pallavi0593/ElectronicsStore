package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.AddItemToCartRequest;
import com.Bikkadit.ElectronicsStore.dtos.CartDto;

public interface CartService {

    //Add Item To cart
    //Case !:
    //CAse 2:

     CartDto addItemToCart(String userId, AddItemToCartRequest request);


}
