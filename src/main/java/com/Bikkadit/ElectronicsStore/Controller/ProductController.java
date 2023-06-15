package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.ProductService;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.ProductDto;
import com.Bikkadit.ElectronicsStore.helper.ApiResponse;
import com.Bikkadit.ElectronicsStore.helper.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {
     @Autowired
     private ProductService productService;
    @PostMapping("/product")
    public ResponseEntity< ProductDto> createUser(@Valid @RequestBody ProductDto productDto)
    {
        log.info("Request Entering to create product In Service:{}",productDto);
       ProductDto product = productService.createProduct(productDto);
        log.info("Product  Created Successfully ");
        return  new ResponseEntity<>(product , HttpStatus.CREATED);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> Updateuser(@Valid @RequestBody ProductDto productDto, @PathVariable String productId)
    {
        log.info("Request Entering Into Service To Update Product With productId:{}",productId);
   ProductDto productDto1 = productService.UpdateProduct(productDto, productId);
        log.info("Product Updated Successfully With productId:{}",productId);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);

    }

    @DeleteMapping("/{productId}")

     public  ResponseEntity<ApiResponse> deleteUser(@PathVariable String productId)
    {
        log.info("Request Entering Into service to delete Product with productId:{}",productId);
        productService.deleteProduct(productId);
        log.info("Record Deleted Successfully With productId:{}",productId);
         return new ResponseEntity<ApiResponse>(new ApiResponse("Record Deleted Successfully",true,HttpStatus.OK),HttpStatus.OK);

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getUserById(@PathVariable String productId)
    {
        log.info("Request Entering into Service Layer to get Product With productId:{}",productId);
     ProductDto productDto = productService.getProductById(productId);
        log.info("Get Product Successfully With productId:{}",productId);
        return new ResponseEntity<>(productDto,HttpStatus.FOUND);
    }

    @GetMapping("/Products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue =AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY_USER, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)

    {
        log.info("Request To get All product from Database");
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        log.info("Get All Users Successfully");
        return  new ResponseEntity<>(allProduct,HttpStatus.OK);
    }
    @GetMapping("/search/{title}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable String title,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue =AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY_USER, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)

    {
        log.info("Request To get All users from Database");
         PageableResponse<ProductDto> productDtoPageableResponse = productService.SearchByTitle(title, pageNumber, pageSize, sortBy, sortDir);
        log.info("Request to get Product by Particular Keyword");
        return  new ResponseEntity<>(productDtoPageableResponse,HttpStatus.OK);
          }
    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue =AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY_USER, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)

    {
        log.info("Request To get All users from Database");
        PageableResponse<ProductDto> allProduct = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("Get All Users Successfully");
        return  new ResponseEntity<>(allProduct,HttpStatus.OK);
    }}
