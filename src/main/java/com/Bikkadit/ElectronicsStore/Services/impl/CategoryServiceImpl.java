package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.CategoryService;
import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.Category;
import com.Bikkadit.ElectronicsStore.entities.User;
import com.Bikkadit.ElectronicsStore.exceptions.ResourceNotFoundException;
import com.Bikkadit.ElectronicsStore.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ModelMapper mapper;

    private static  final Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("Request proceed to create Category in Persistence Layer");
   Category category = this.mapper.map(categoryDto, Category.class);
    Category category1 = categoryRepo.save(category);
      CategoryDto newCategory = mapper.map(category1, CategoryDto.class);
        logger.info("Category record saved Successfully in database");
        return newCategory;

    }

    @Override
    public CategoryDto UpdateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Request proceed to update User in Persistence Layer with userId:{}",categoryId);
      Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
       category.setTitle(categoryDto.getTitle());
        category.setDesciption(categoryDto.getDesciption());
      category.setCoverImage(categoryDto.getCoverImage());

       Category updatedCategory = categoryRepo.save(category);
        logger.info("Category Updated Successfully in database with categoryId:{}",categoryId);
        return this.mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String userId) {

    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        return null;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<CategoryDto> SearchCategory(String keyword) {
        return null;
    }
}
