package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.entities.Category;
import com.Bikkadit.ElectronicsStore.entities.Product;
import com.Bikkadit.ElectronicsStore.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

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
}
