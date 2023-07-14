package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.CategoryService;
import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;
    @InjectMocks
    private  CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;

    private  CategoryDto categoryDto;
    @BeforeEach                //run before All junit test
    public void setup()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();  //for Background initialization


        categoryDto=CategoryDto.builder().categoryId("1").title("Android Mobile").desciption("Supports 5G").coverImage("XYZ.jpg").build();
        //if we don't call below, we will get NullPointerException

        //standAloneSetup static method seperately setup our class whose instace we are provided
    }
    private String convertObjectToJsonString(Object categoryDto){

        try{
            return new ObjectMapper().writeValueAsString(categoryDto);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
@Test
    public void createCategory() throws Exception {
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);

        mockMvc.perform(post("/Categories/CreateCategory").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonString(categoryDto)).accept(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Android Mobile"));
    }
    @Test
    public void  UpdatecategoryTest() throws Exception {
        String categoryId = "1";

        CategoryDto categoryDto = CategoryDto.builder().categoryId("1").title("updated Mobile")
                .desciption("Supports 5G").coverImage("XYZ.jpg").build();
        Mockito.when(categoryService.UpdateCategory(Mockito.any(), Mockito.anyString())).thenReturn(categoryDto);

        mockMvc.perform(put("/Categories/updateCategory/" + categoryId).contentType(MediaType.APPLICATION_JSON).
                content(convertObjectToJsonString(categoryDto)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("updated Mobile"));
    }
    @Test
public void getCategoryByIdTest() throws Exception {
   String categoryId="1";

   Mockito.when(categoryService.getCategoryById(Mockito.anyString())).thenReturn(categoryDto);

   mockMvc.perform(get("/Categories/"+categoryId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isFound());
}
@Test
public void getAllCategoryTest() throws Exception {
    CategoryDto categoryDto1 = CategoryDto.builder().categoryId("1").title("updated Mobile")
            .desciption("Supports 5G").coverImage("XYZ.jpg").build();
    CategoryDto categoryDto2 = CategoryDto.builder().categoryId("12").title("updated Mobile")
            .desciption("Supports 5G").coverImage("poi.jpg").build();
    CategoryDto categoryDto3 = CategoryDto.builder().categoryId("13").title("updated Mobile")
            .desciption("Supports 5G").coverImage("abc.jpg").build();
    CategoryDto categoryDto4 = CategoryDto.builder().categoryId("14").title("updated Mobile")
            .desciption("Supports 5G").coverImage("mnl.jpg").build();
    PageableResponse pageableResponse=new PageableResponse<>();
    pageableResponse.setContent(Arrays.asList(categoryDto1,categoryDto1,categoryDto1,categoryDto1));

    pageableResponse.setLastpage(false);
    pageableResponse.setTotalElements(100l);
    pageableResponse.setTotalPages(1000);
    pageableResponse.setPageSize(10);
    pageableResponse.setPageNumber(1);
    Mockito.when(categoryService.getAllCategory(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

    mockMvc.perform(get("/Categories/Category").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
}
@Test
public  void   deleteCategoryTest()
{

    String categoryId="";

categoryService.deleteCategory(categoryId);
    verify(categoryService, times(1)).deleteCategory(categoryId);
}
@Test
public  void  searchCategoryTest() throws Exception {
    String keyword="Mobile";
    CategoryDto categoryDto1 = CategoryDto.builder().categoryId("1").title("updated Mobile")
        .desciption("Supports 5G").coverImage("XYZ.jpg").build();
    CategoryDto categoryDto2 = CategoryDto.builder().categoryId("12").title("updated Mobile")
            .desciption("Supports 5G").coverImage("poi.jpg").build();
    List<CategoryDto> l=new ArrayList<>();
    l.add(categoryDto1);
    l.add(categoryDto2);

    Mockito.when(categoryService.SearchCategory(Mockito.anyString())).thenReturn(l);

    mockMvc.perform(get("/Categories/search/"+keyword).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
            andExpect(status().isOk()).andDo(print());
}
}
