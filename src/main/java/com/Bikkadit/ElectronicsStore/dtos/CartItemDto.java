package com.Bikkadit.ElectronicsStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Integer cartItemId;

    private ProductDto product;

    private Integer quantity;

    private Integer totalPricae;

}
