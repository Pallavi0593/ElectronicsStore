package com.Bikkadit.ElectronicsStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(name="Cart")
public class Cart {
    @Id
    private String cartId;

    private Date createdAt;
@OneToOne
    private  User user;
@OneToMany(mappedBy ="cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> cartItem=new ArrayList<>();
}
