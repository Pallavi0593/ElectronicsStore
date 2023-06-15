package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.ProductDto;

public interface ProductService {

    //Create
   ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto  UpdateProduct(ProductDto productDto,String productId);
    //delete
    void deleteProduct(String productId);



    //getUserById
    ProductDto getProductById(String productId);
    //getAllUser

    PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

 PageableResponse<ProductDto> SearchByTitle(String title,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
