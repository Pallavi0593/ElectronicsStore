package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.entities.Category;
import com.Bikkadit.ElectronicsStore.exceptions.ResourceNotFoundException;
import com.Bikkadit.ElectronicsStore.repositories.CategoryRepository;
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
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    private Category category;

    private  Category category1;
    @BeforeEach
    public void init(){

        category= Category.builder()
                .title("Mobiles").
                desciption("These are available")
                .coverImage("mobi.png")
                .build();

        category1= Category.builder()
                .title("Machine")
                .desciption("Machines are available")
                .coverImage("machine.png")
                .build();

        String categoryId = "tyu67";

    }
    @Test
    public void  createCategoryTest() {

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto categoryDto = categoryService.createCategory(this.mapper.map(category, CategoryDto.class));

        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals("Mobiles", categoryDto.getTitle());

    }

    @Test
    public void UpdateCategoryTest() {
        String categoryId = "tyu67";
        CategoryDto categoryDto= CategoryDto.builder()
                .title("Mobiles")
                .desciption("Mobile phones are available")
                .coverImage("phone.png")
                .build();

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto updateCategory = categoryService.UpdateCategory(categoryDto, categoryId);

        Assertions.assertNotNull(updateCategory);
        Assertions.assertEquals(categoryDto.getTitle(), updateCategory.getTitle());
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> categoryService.UpdateCategory(categoryDto, "56"));

    }

    @Test
    public void deleteCategoryTest() {
        String categoryId = "tyu67";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(categoryId);

        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> categoryService.deleteCategory("56"));

    }

    @Test
    public void ggetCategoryByIdTest() {
        String categoryId = "tyu67";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(), categoryDto.getTitle(), "Titles not matched");
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> categoryService.getCategoryById("56"));

    }

    @Test
    public void getAllCategoryTest() {

        Category category2= Category.builder()
                .title("HeadPhones")
                .desciption("Headphones are available")
                .coverImage("headphone.png")
                .build();

        List<Category> categoryList = Arrays.asList(category, category1, category2);

        Page<Category> page=new PageImpl<>(categoryList);

        Mockito.when(categoryRepository.findAll((Pageable)Mockito.any())).thenReturn(page);

        PageableResponse<CategoryDto> allCategories = categoryService.getAllCategory(1, 3, "title", "asc");

        Assertions.assertEquals(3, allCategories.getContent().size());
    }

    @Test
    public void SearchCategoryTest() {

        String keyword= "m";

        List<Category> categoryList = Arrays.asList(category, category1);

        Mockito.when(categoryRepository.findByTitleContaining(Mockito.anyString())).thenReturn(categoryList);

        List<CategoryDto> categoryDtoList = categoryService.SearchCategory(keyword);

        Assertions.assertEquals(2, categoryDtoList.size());

    }
}




