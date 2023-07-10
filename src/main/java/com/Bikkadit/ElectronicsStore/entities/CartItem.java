package com.Bikkadit.ElectronicsStore.entities;

import javax.persistence.*;

@Entity
@Table(name="Cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    @OneToOne
    @JoinColumn(name = "product_id")
    private  Product product;

    private Integer quantity;

    private Integer totalPricae;
    @ManyToOne
@JoinColumn(name = "cart_id")
    private  Cart cart;
}
