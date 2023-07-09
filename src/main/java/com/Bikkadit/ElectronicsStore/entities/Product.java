package com.Bikkadit.ElectronicsStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product_Details")
public class Product {
@Id
    private String productId;

    private String title;
@Column(length=10000)
    private String description;

    private Integer price;

    private Integer discountedPrice;

    private Integer quantity;

    private Date addedDate;

    private Boolean live;
    private Boolean stock;

    private String productImage;
@ManyToOne(fetch =FetchType.EAGER)
@JoinColumn(name = "category_id")
private Category category;

}
