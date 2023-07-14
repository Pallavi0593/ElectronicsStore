package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.ProductService;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @InjectMocks
    private  ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    private  ProductDto productDto;
    @BeforeEach                //run before All junit test
    public void setup()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();  //for Background initialization

  productDto = ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        //if we don't call below, we will get NullPointerException

        //standAloneSetup static method seperately setup our class whose instace we are provided
    }
    private String convertObjectToJsonString(Object productDto){

        try{
            return new ObjectMapper().writeValueAsString(productDto);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Test
    public void createProductTest() throws Exception {
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDto);

        mockMvc.perform(post("/api/product").contentType(MediaType.APPLICATION_JSON).
                content(convertObjectToJsonString(productDto)).accept(MediaType.APPLICATION_JSON)).andDo(print()).
                andExpect(status().isCreated()).andExpect(jsonPath("$.title").exists());

    }
    @Test
    public void UpdateProductTest() throws Exception {
        String productId="1";
       ProductDto productDto = ProductDto.builder().productId("1").title("Mobile Updated").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();

        Mockito.when(productService.UpdateProduct(Mockito.any(),Mockito.anyString())).thenReturn(productDto);

        mockMvc.perform(put("/api/product/"+productId).contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonString(productDto)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("" +
                "Mobile Updated"));
    }
    @Test
    public void deleteProduct()
    {
        String productId="";
        productService.deleteProduct(productId);

        verify(productService, times(1)).deleteProduct(productId);
    }
  @Test
    public void getProductById() throws Exception {
        String productId="1";
        Mockito.when(productService.getProductById(productId)).thenReturn(productDto);
        mockMvc.perform(get("/api//product/"+productId).contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonString(productDto)).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isFound()).andExpect(jsonPath("$.title").value("Mobile"));
    }
    @Test
    public  void getAllProduct() throws Exception {
       ProductDto productDto1 = ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto2=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto3=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto4=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();

        PageableResponse pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(productDto1,productDto2,productDto3,productDto4));

        pageableResponse.setLastpage(false);
        pageableResponse.setTotalElements(100l);
        pageableResponse.setTotalPages(1000);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(1);
       Mockito.when(productService.getAllProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

       mockMvc.perform(get("/api/Products").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
   @Test
    public  void  searchProductTest() throws Exception {
        String title="Mobile";
        ProductDto productDto1 = ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto2=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto3=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto4=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();

        PageableResponse pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(productDto1,productDto2,productDto3,productDto4));

        pageableResponse.setLastpage(false);
        pageableResponse.setTotalElements(100l);
        pageableResponse.setTotalPages(1000);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(1);
        Mockito.when(productService.SearchByTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(get("/api/search/"+title).contentType(MediaType.APPLICATION_JSON).content(Arrays.asList(productDto1,productDto2,productDto3,productDto4).stream().map(e->convertObjectToJsonString(e)).toString()).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
    @Test
    public  void getAllLiveTest() throws Exception {
        ProductDto productDto1 = ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto2=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto3=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();
        ProductDto productDto4=ProductDto.builder().productId("1").title("Mobile").addedDate(new Date()).price(50000).productImage("xyz.jpg").
                live(true).description("Mobile With updated Features").stock(true).discountedPrice(44000).quantity(10).build();

        PageableResponse pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(productDto1,productDto2,productDto3,productDto4));

        pageableResponse.setLastpage(false);
        pageableResponse.setTotalElements(100l);
        pageableResponse.setTotalPages(1000);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(1);

        Mockito.when(productService.getAllLive(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(get("/api/live")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
}
