package com.Bikkadit.ElectronicsStore.Services;

import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.User;
import com.Bikkadit.ElectronicsStore.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {
     @MockBean
     private UserRepo userRepo;

   @Autowired
   private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user;
    @BeforeEach
    public void init()
    {
      user= User.builder().name("pallavi").email("pallavi@gmail.com").gender("Female").about("I am software Engineer")
              .password("paLLavi").imageName("default.jpg").build();
    }
//Create user
    @Test
    public void createUserTest()
    {

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

         UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));

         System.out.println(user1.getName());
        Assertions.assertNotNull(user1);

        Assertions.assertEquals("pallavi",user1.getName());
    }
@Test
    public  void UpdateUserTest()
    {
        String userId="";
        UserDto userDto=new UserDto();
        userDto.setName("pallavi Tejas Yeola");
       userDto.setAbout("I am software Engineer with updated Technology");
       userDto.setImageName("xyz.jpg");


        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

       UserDto updateUser = userService.UpdateUser( userDto,userId);
System.out.println(updateUser.getName());
Assertions.assertNotNull(updateUser);
Assertions.assertEquals(userDto.getName(),updateUser.getName());

    }
@Test
    public void deleteuserTest(){

        String userId="";
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        userService.deleteuser(userId);
        Mockito.verify(userRepo,Mockito.times(1)).delete(user);
}
@Test
public void getAllUserTest(){
     User user1= User.builder().name("KAlyani").email("pallavi@gmail.com").gender("Female").about("I am software Engineer")
            .password("paLLavi").imageName("default.jpg").build();
    User  user2= User.builder().name("Rian").email("pallavi@gmail.com").gender("Female").about("I am software Engineer")
                            .password("paLLavi").imageName("default.jpg").build();
    User  user3= User.builder().name("Kammini").email("pallavi@gmail.com").gender("Female").about("I am software Engineer")
                                            .password("paLLavi").imageName("default.jpg").build();


    List<User> list= Arrays.asList(user,user1,user2,user3);
    Page<User> page= new PageImpl<>(list);
  Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
  PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
  Assertions.assertEquals(4,allUser.getContent().size());
}
@Test
public void getUserByIdTest()
{

    Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));

UserDto userById = userService.getUserById(Mockito.anyString());

Assertions.assertNotNull(userById);

Assertions.assertEquals(user.getName(),userById.getName());
}
@Test
public void getUserByEmailTest()
{
    Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

   UserDto userByEmail = userService.getUserByEmail(Mockito.anyString());

   Assertions.assertNotNull(userByEmail);

   Assertions.assertEquals(user.getName(),userByEmail.getName());


}
@Test
public void SearchUserTest()
{
    String keyword="p";
    User user1= User.builder().name("KAlyani").email("kalyani@gmail.com").gender("Female").about("I am software Engineer")
            .password("kaLYani").imageName("default.jpg").build();
    User  user2= User.builder().name("Rian").email("rina@gmail.com").gender("Female").about("I am software Engineer")
            .password("riNna29").imageName("default.jpg").build();
    User  user3= User.builder().name("Kammini").email("kaamini@gmail.com").gender("Female").about("I am software Engineer")
            .password("kaAMini").imageName("default.jpg").build();

    Mockito.when(userRepo.findByNameContaining("p")).thenReturn(Arrays.asList(user,user1,user2,user3));

    List<UserDto> userDtos = userService.SearchUser(keyword);

    Assertions.assertEquals(4,userDtos.size());
}
@Test
public void getUserByEmailOptionalTest()
{
    Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

    Optional<User> userByEmailOptional = userService.getUserByEmailOptional(Mockito.anyString());

    Assertions.assertTrue(userByEmailOptional.isPresent());

  User user1 = userByEmailOptional.get();

  Assertions.assertEquals(user.getEmail(),user1.getEmail());


}
}

