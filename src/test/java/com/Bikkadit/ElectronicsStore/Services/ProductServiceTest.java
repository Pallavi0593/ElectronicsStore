package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.ProductDto;
import com.Bikkadit.ElectronicsStore.entities.Category;
import com.Bikkadit.ElectronicsStore.entities.Product;
import com.Bikkadit.ElectronicsStore.exceptions.ResourceNotFoundException;
import com.Bikkadit.ElectronicsStore.repositories.CategoryRepository;
import com.Bikkadit.ElectronicsStore.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
@MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    private Product product;
    private Category category;
    @BeforeEach
    public void init()
    {
        product = Product.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        product.setCreatedBy("Pallavi");
        product.setLastModifiedBy("Pallavi");
        product.setIsactive("Active");
        category = Category.builder().categoryId("1").title("updated Mobile")
                .desciption("Supports 5G").coverImage("XYZ.jpg").build();
        product.setCategory(category);
    }
   @Test
    public  void createProductTest()
    {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

  ProductDto productDto = productService.createProduct(mapper.map(product, ProductDto.class));

        Assertions.assertEquals("Mobile",productDto.getTitle());
        Assertions.assertNotNull(productDto);

    }
    @Test
    public void updateProductTest() {

        ProductDto productDto= ProductDto.builder()
                .title("Nokia X2")
                .description("This is also old version")
                .price(10000)
                .discountedPrice(1000)
                .quantity(8)
                .live(true)
                .stock(true)
                .productImage("x2.png")
                .build();

        String productId="hjjkk789";

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto updateProduct = productService.UpdateProduct(productDto, productId);
        System.out.println(updateProduct.getTitle());

        Assertions.assertNotNull(updateProduct);
        Assertions.assertEquals(productDto.getTitle(), updateProduct.getTitle());
        Assertions.assertThrows(ResourceNotFoundException.class, ()->productService.UpdateProduct(productDto, "2"));


    }

    @Test
    public void deleteProductTest() {

        String productId="yui6778";

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> productService.deleteProduct("1"));
    }
    @Test
    public void getProductBYIdTest() {

        String productId="yui6778";

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDto productDto = productService.getProductById(productId);
        System.out.println(productDto.getTitle());

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getPrice(), productDto.getPrice(), "Price Not Matched");
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> productService.getProductById("1"));

    }
    @Test
    public void getAllProductsTest() {

        Product product1= Product.builder()
                .title("Nokia X2")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .build();

        Product product2= Product.builder()
                .title("Nokia X4")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .build();

        List<Product> productList = Arrays.asList(product, product1, product2);
        Page<Product> page=new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> pageableResponse = productService.getAllProduct(1,3,"title", "asc");

        //       Assertions.assertEquals(3, pageableResponse.getContent().size());    // Same as below line
        Assertions.assertEquals(3, pageableResponse.getPageSize());


    }

    @Test
    public void  getAllLiveTest() {

        Product product1= Product.builder()
                .title("Nokia X2")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .build();

        Product product2= Product.builder()
                .title("Nokia X4")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .build();

        List<Product> productList = Arrays.asList(product, product1, product2);
        Page<Product> page=new PageImpl<>(productList);

        Mockito.when(productRepository.findByLiveTrue(Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(1, 3, "title", "desc");

        Assertions.assertEquals(3, pageableResponse.getPageSize());
        Assertions.assertEquals(0, pageableResponse.getPageNumber());


    }

    @Test
    public void SearchByTitleTest() {

        Product product1= Product.builder()
                .title("Nokia X2")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .build();

        Product product2= Product.builder()
                .title("Nokia X4")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .build();

        String title="kia";

        List<Product> productList = Arrays.asList(product, product1, product2);
        Page<Product> page=new PageImpl<>(productList);

        Mockito.when(productRepository.findByTitleContaining(Mockito.anyString(), Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> pageableResponse = productService.SearchByTitle(title, 1, 3, "title", "desc");

        Assertions.assertEquals(1, pageableResponse.getTotalPages());
        Assertions.assertEquals(page.getContent().size(), pageableResponse.getContent().size());


    }

    @Test
    public void createProductWithCategoryTest() {

        String categoryId="56";

        Product product1= Product.builder()
                .title("Nokia X3")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .category(category)
                .build();


        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product1);

        ProductDto dto = this.mapper.map(product, ProductDto.class);
        ProductDto productDto = productService.createProductWithCategory(dto, categoryId);

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(category.getTitle(), productDto.getCategory().getTitle());
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> productService.createProductWithCategory(dto,"55"));


    }
    @Test
    public void updateCategoryTest() {

        Product product1= Product.builder()
                .title("Nokia X3")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .category(category)
                .build();

        String productId= "uio67";
        String categoryId= "dfgg34";

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product1);

        ProductDto productDto = productService.updateCategory(categoryId, productId);

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(category.getTitle(), productDto.getCategory().getTitle());
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> productService.updateCategory("67",productId));
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> productService.updateCategory(categoryId,"55"));

    }
    @Test
    public void getProductsByCategoryId() {

        Product product1= Product.builder()
                .title("Nokia X3")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .category(category)
                .build();

        Product product2= Product.builder()
                .title("Nokia X2")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .category(category)
                .build();

        Product product3= Product.builder()
                .title("Nokia X4")
                .description("This is old version")
                .price(12000)
                .discountedPrice(2000)
                .quantity(10)
                .live(true)
                .stock(true)
                .productImage("x3.png")
                .category(category)
                .build();

        String categoryId= "gjhjjku";

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        List<Product> productList= Arrays.asList(product1, product2, product3);
        Page<Product> page=new PageImpl<>(productList);

        Mockito.when(productRepository.findByCategory(Mockito.any(), (Pageable)Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> pageableResponse = productService.getallproductofCategory(categoryId, 1, 3, "title", "asc");

        Assertions.assertEquals(page.getSize(), pageableResponse.getPageSize());
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> productService.getallproductofCategory("yui78",1,3,"title","asc"));

    }
}
