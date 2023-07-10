package com.Bikkadit.ElectronicsStore.entities;

import javax.persistence.*;

@Entity
@Table(name="Cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    @OneToOne
    private  Product product;

    private Integer quantity;

    private Integer totalPricae;
}
