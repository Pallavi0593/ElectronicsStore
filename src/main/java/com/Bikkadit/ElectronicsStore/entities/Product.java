package com.Bikkadit.ElectronicsStore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product_Details")
@Builder
public class Product extends CustomFields{
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



}
