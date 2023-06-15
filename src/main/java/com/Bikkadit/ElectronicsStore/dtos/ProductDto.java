package com.Bikkadit.ElectronicsStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {


    private String productId;

    @Size(max = 20,message = "Title  Must be  5 character")
    private String title;
    @Size(min=5,max = 20,message = "Description  Must be  5 character")
    private String description;

    @NotNull
    private Integer price;

    private Integer discountedPrice;

    @NotNull
    private Integer quantity;

    private Date addedDate;
    @Size(min=5,max = 20,message = "Name Must be  5 character")
    private Boolean live;
    private Boolean stock;
}
