package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.CartService;
import com.Bikkadit.ElectronicsStore.dtos.AddItemToCartRequest;
import com.Bikkadit.ElectronicsStore.dtos.CartDto;
import com.Bikkadit.ElectronicsStore.entities.Cart;
import com.Bikkadit.ElectronicsStore.entities.CartItem;
import com.Bikkadit.ElectronicsStore.entities.Product;
import com.Bikkadit.ElectronicsStore.entities.User;
import com.Bikkadit.ElectronicsStore.exceptions.BadApiRequestException;
import com.Bikkadit.ElectronicsStore.exceptions.ResourceNotFoundException;
import com.Bikkadit.ElectronicsStore.repositories.CartItemRepository;
import com.Bikkadit.ElectronicsStore.repositories.CartRepository;
import com.Bikkadit.ElectronicsStore.repositories.ProductRepository;
import com.Bikkadit.ElectronicsStore.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

       String productId = request.getProductId();
       Integer quantity = request.getQuantity();
       if (quantity<=0)
       {
           throw new BadApiRequestException("Requested Quantity is not valid!!");
       }
       //Fetch The product
       Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
//fetch the User from Darabase
     User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

     Cart cart=null;
     try {
  cart = cartRepository.findByUser(user).get();
     }catch (NoSuchElementException e)
     {
         cart=new  Cart();
         cart.setCartId(UUID.randomUUID().toString());
         cart.setCreatedAt(new Date());
     }

     //Perform Cart Operation
        //If Cart item Already Present Then update Cart
        AtomicBoolean updated= new AtomicBoolean(false);
        List<CartItem> cartItem = cart.getCartItem();
        List<CartItem> updatedList = cartItem.stream().map(item -> {
                    if (item.getProduct().getProductId().equals(productId)) {
                        //product Already present
                        item.setQuantity(quantity);
                        item.setTotalPricae(quantity * product.getPrice());
                         updated.set(true);
                    }
                    return item;
                }

        ).toList();

cart.setCartItem(updatedList);

        if(!updated.get()) {
    CartItem item = CartItem.builder().quantity(quantity).product(product)
            .cart(cart).totalPricae(quantity * product.getPrice()).build();
    cart.getCartItem().add(item);
}


         cart.setUser(user);

     Cart cart1= cartRepository.save(cart);
        return mapper.map(cart1,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItemId) {
     CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart item Not fount"));
cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String userId) {

     User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
       Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found For Usre"));
       cart.getCartItem().clear();
       cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return mapper.map(cart,CartDto.class);
    }
}
