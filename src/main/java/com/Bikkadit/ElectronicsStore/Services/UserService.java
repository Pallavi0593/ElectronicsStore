package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.User;

import java.util.List;

public interface UserService {

    //Create
UserDto createUser(UserDto userDto);

    //update
    UserDto UpdateUser(UserDto userDto,String userid);
    //delete
void deleteuser(String userId);



    //getUserById
UserDto getUserById(String userId);
    //getAllUser

 PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    UserDto getUserByEmail(String email);

    public  List<UserDto> SearchUser(String keyword);
}
