package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;

import java.util.List;

public interface CategoryService {
    UserDto createCategory(CategoryDto categoryDto);

    //update
    UserDto UpdateUser(CategoryDto categoryDto,String categoryId);
    //delete
    void deleteuser(String userId);



    //getUserById
    UserDto getUserById(String userId);
    //getAllUser

    PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    UserDto getUserByEmail(String email);

    public List<UserDto> SearchUser(String keyword);
}
}
