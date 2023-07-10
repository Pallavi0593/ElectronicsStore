package com.Bikkadit.ElectronicsStore.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name="Cart")
public class Cart {
    @Id
    private String cartId;

    private Date createdAt;

    private  User user;

    private  CartItem cartItem;
}
