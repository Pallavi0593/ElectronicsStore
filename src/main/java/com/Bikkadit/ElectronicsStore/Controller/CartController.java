package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.CartService;
import com.Bikkadit.ElectronicsStore.dtos.AddItemToCartRequest;
import com.Bikkadit.ElectronicsStore.dtos.CartDto;
import com.Bikkadit.ElectronicsStore.helper.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")

public class CartController {
    @Autowired
    private CartService cartService;
    //add Item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request)
    {
    CartDto cartDto = cartService.addItemToCart(userId, request);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);


    }
    @DeleteMapping("{userId}/items/{itemId}")
public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId,@PathVariable Integer itemId)
{
cartService.removeItemFromCart(userId,itemId);
 ApiResponse apiResponse = ApiResponse.builder().message("Item Removed From Cart").success(true).status(HttpStatus.OK).build();
    return new ResponseEntity<>(apiResponse,HttpStatus.OK);
}
    @DeleteMapping("{userId}")
public  ResponseEntity<ApiResponse> clearCart(@PathVariable String userId)
    {
        cartService.clearCart(userId);
        ApiResponse apiResponse = ApiResponse.builder().message("Item Removed From Cart").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public  ResponseEntity<CartDto> getCart(@PathVariable String userId)
    {
     CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }
}
