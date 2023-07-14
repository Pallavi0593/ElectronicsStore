package com.Bikkadit.ElectronicsStore.dtos;

import com.Bikkadit.ElectronicsStore.util.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto extends CustomFieldDto{


    private String productId;

    @Size(max = 20,message = "Title  Must be  5 character")
    private String title;
    @Size(min=5,message = "Description  Must be  5 character")
    private String description;

    @NotNull
    private Integer price;

    private Integer discountedPrice;

    @NotNull
    private Integer quantity;

    private Date addedDate;

    private Boolean live;
    private Boolean stock;

    @ImageNameValid
    private String productImage;

    private CategoryDto category;
}
