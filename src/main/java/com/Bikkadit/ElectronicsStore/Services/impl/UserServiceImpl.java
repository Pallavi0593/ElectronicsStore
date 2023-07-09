package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.UserService;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.User;
import com.Bikkadit.ElectronicsStore.exceptions.ResourceNotFoundException;
import com.Bikkadit.ElectronicsStore.helper.ForPagination;
import com.Bikkadit.ElectronicsStore.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper mapper;
    @Value("${user.profile.image.paths}")
    private String imageUploadPath;
    private static  final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDto createUser(UserDto userDto) {
logger.info("Request proceed to create User in Persistence Layer");
        String userId = UUID.randomUUID().toString();
        userDto.setUserid(userId);
        User user =  this.mapper.map(userDto,User.class);
       // User user = this.DtoToEntity(userDto);
        User user1 = userRepo.save(user);
        UserDto userDto1 = this.EntityToDto(user1);
        logger.info("User saved Successfully in database");
        return userDto1;
    }


    @Override
    public UserDto UpdateUser(UserDto userDto, String userId) {
        logger.info("Request proceed to update User in Persistence Layer with userId:{}",userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
      userRepo.save(user);
        logger.info("User Updated Successfully in database with userId:{}",userId);
        return this.mapper.map(user,UserDto.class);
    }


    @Override
    public void deleteuser(String userId) {
        logger.info("Request proceed to Delete User in Persistence Layer with userId:{}",userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        String fullPath= imageUploadPath + user.getImageName();

        try{
      Path path = Paths.get(fullPath);

            Files.delete(path);
        } catch(NoSuchFileException ex)
        {
            ex.printStackTrace();
        }

        catch (IOException e)
        {
            e.printStackTrace();
         // throw  new RuntimeException(e);
        }
        userRepo.delete(user);
        logger.info("User Deleted Successfully in Database with userId:{}",userId);
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Request proceed to get Single User in Persistence Layer with userId:{}",userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
        logger.info("User Get from DataBase with userId:{}",userId);
        return this.EntityToDto(user);
    }


    @Override
    public PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Request proceed  in Persistance Layer to get All User From Database");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //Sort sort = Sort.by(sortBy)ascending();//only for SortBy
        Pageable pageable=  PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page = userRepo.findAll(pageable);

         PageableResponse<UserDto> pageableResponse = ForPagination.getPageableResponse(page, UserDto.class);
         return pageableResponse;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Request proceeding Persistence Layer to get Single User   using Email:{}",email);
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User","email",email));
        logger.info("Get  User From Database using Email Address");
        return this.mapper.map(user,UserDto.class);

    }



    @Override
    public List<UserDto> SearchUser(String keyword) {
        logger.info("Request proceed  in Persistence Layer to get User using keyword:{}",keyword);
        List<User> users= userRepo.findByNameContaining(keyword);
        List<UserDto> userDto = users.stream().map(user -> mapper.map(user,UserDto.class)).collect(Collectors.toList());
        logger.info("Get All User From Database using keyword");
        return userDto;
    }

    @Override
    public Optional<User> getUserByEmailOptional(String email) {

   return userRepo.findByEmail(email);
    }


    private UserDto EntityToDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        /*UserDto userDto=UserDto.builder().
                userid(user.getUserid()).
                name(user.getName()).
                email(user.getEmail()).
                gender(user.getGender()).
                password(user.getPassword()).
                imageName(user.getImageName()).
                about(user.getAbout()).build();*/
        return userDto;
    }

    
  /*  private User DtoToEntity(UserDto userDto) {
        User user = User.builder().userid(userDto.getUserid()).
                name(userDto.getName()).email(userDto.getEmail()).
                password(userDto.getPassword()).gender(userDto.getGender()).
                imageName(userDto.getImageName()).about(userDto.getAbout()).build();
        return user;
    }*/
}
