package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto UpdateCategory(CategoryDto categoryDto, String categoryId);
    //delete
    void deleteCategory(String categoryId);



    //getUserById
    CategoryDto getCategoryById(String categoryId);
    //getAllUser

    PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);



    public List<CategoryDto> SearchCategory(String keyword);
}

