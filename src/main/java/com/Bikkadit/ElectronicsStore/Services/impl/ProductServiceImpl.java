package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.ProductService;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.ProductDto;
import com.Bikkadit.ElectronicsStore.entities.Category;
import com.Bikkadit.ElectronicsStore.entities.Product;
import com.Bikkadit.ElectronicsStore.exceptions.ResourceNotFoundException;
import com.Bikkadit.ElectronicsStore.helper.ForPagination;
import com.Bikkadit.ElectronicsStore.repositories.CategoryRepository;
import com.Bikkadit.ElectronicsStore.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${product.profile.image.paths}")
    private String imageUploadPath;
    private static  final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public ProductDto createProduct(ProductDto productDto) {
    Product product = this.mapper.map(productDto, Product.class);
        String Id = UUID.randomUUID().toString();
        product.setProductId(Id);
        product.setAddedDate(new Date());
         Product product1 = productRepository.save(product);
         ProductDto productDto1 = mapper.map(product1, ProductDto.class);

        logger.info("Product saved Successfully in database");
        return productDto1;
    }

    @Override
    public ProductDto UpdateProduct(ProductDto productDto, String productId) {
        logger.info("Request proceed to update Product in Persistence Layer with productId:{}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("User", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.getLive());
        product.setProductImage(productDto.getProductImage());
        Product product1 = productRepository.save(product);
        logger.info("User Updated Successfully in database with userId:{}",productId);
        return this.mapper.map(product1,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        logger.info("Request proceed to Delete Product in Persistence Layer with productId:{}",productId);
     Product product= productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));
        String fullPath= imageUploadPath + product.getProductImage();
        try{
            Path path = Paths.get(fullPath);

            Files.delete(path);
        } catch(NoSuchFileException ex)
        {
            ex.printStackTrace();
        }

        catch (IOException e)
        {
            e.printStackTrace();
            // throw  new RuntimeException(e);
        }
        productRepository.delete(product);
        logger.info("Product details Deleted Successfully in Database with productId:{}",productId);
    }

    @Override
    public ProductDto getProductById(String productId) {
        logger.info("Request proceed to get Single User in Persistence Layer with productId:{}",productId);
      Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));
        logger.info("User Get from DataBase with userId:{}",productId);
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Request proceed  in Persistence Layer to get All Product  From Database");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //Sort sort = Sort.by(sortBy)ascending();//only for SortBy
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = ForPagination.getPageableResponse(products, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Request proceed  in Persistence Layer to get All Live Product  From Database ");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //Sort sort = Sort.by(sortBy)ascending();//only for SortBy
        Pageable pageable=  PageRequest.of(pageNumber,pageSize,sort);
    Page<Product> products = productRepository.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = ForPagination.getPageableResponse(products, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> SearchByTitle(String title,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Request proceed  in Persistence Layer to get All Products From Database using title");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //Sort sort = Sort.by(sortBy)ascending();//only for SortBy
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findByTitleContaining(title,pageable);

        final PageableResponse<ProductDto> pageableResponse = ForPagination.getPageableResponse(products, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
     //fetch category
       Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        Product product = this.mapper.map(productDto, Product.class);
        String Id = UUID.randomUUID().toString();
        product.setProductId(Id);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product product1 = productRepository.save(product);

        ProductDto productDto1 = mapper.map(product1, ProductDto.class);

        logger.info("Product saved Successfully in database");
        return productDto1;
    }

    @Override
    public ProductDto updateCategory(String categoryId, String productId) {

 Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with given Id not found"));
    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category With given Id Not found"));
    product.setCategory(category);

    Product savedproduct = productRepository.save(product);
        return mapper.map(savedproduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getallproductofCategory(String categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category With given Id Not found"));

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
     Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> byCategory = productRepository.findByCategory(category,pageable);
        return ForPagination.getPageableResponse(byCategory,ProductDto.class);
    }
}
