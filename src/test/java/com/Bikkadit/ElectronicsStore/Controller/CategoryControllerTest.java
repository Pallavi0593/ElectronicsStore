package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.CategoryService;
import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private String convertObjectToJsonString(Object category){

        try{
            return new ObjectMapper().writeValueAsString(category);
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

}
