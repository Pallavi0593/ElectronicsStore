package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto UpdateUser(CategoryDto categoryDto, String categoryId);
    //delete
    void deleteuser(String userId);



    //getUserById
    CategoryDto getUserById(String categoryId);
    //getAllUser

    PageableResponse<CategoryDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    CategoryDto getUserByEmail(String email);

    public List<CategoryDto> SearchUser(String keyword);
}

