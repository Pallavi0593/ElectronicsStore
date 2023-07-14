package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.FileService;
import com.Bikkadit.ElectronicsStore.Services.UserService;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.User;
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

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration

@ExtendWith(MockitoExtension.class)
public class UserControllTest {

    @MockBean
    private UserService userService;
    @MockBean
private FileService fileService;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private  UserController userController;
    private User user;

    @BeforeEach                //run before All junit test
    public void setup()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();  //for Background initialization
        user= User.builder().name("pallavi").email("pallavi@gmail.com").gender("Female").about("I am software Engineer")
                .password("paLLavi").imageName("default.jpg").build();

        UserDto userDto=new UserDto();
        userDto.setName("pallavi Tejas Yeola");
        userDto.setAbout("I am software Engineer with updated Technology");
        userDto.setGender("female");
        userDto.setEmail("pallavi@gmail.com");
        userDto.setPassword("paLLavi05");
        userDto.setImageName("xyz.jpg");
        //if we don't call below, we will get NullPointerException

        //standAloneSetup static method seperately setup our class whose instace we are provided
    }

    @Test
    public void createUserTest() throws Exception {

        UserDto userDto=new UserDto("1","pallavi Tejas Yeola","pallavi@gmail.com",
                "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");


     when(userService.createUser(any())).thenReturn(userDto);


        mockMvc.perform(post("/users/CreateUser")                      //perform post
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

@Test
public void  UpdateuserTest() throws Exception
{
    String userId="1";



    UserDto userDto=new UserDto("1","pallavi Tejas Yeola","pallavi@gmail.com",
        "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");

  when(userService.UpdateUser(any(), anyString())).thenReturn(userDto);

  mockMvc.perform(put("/users/updatedUser/"+userId)
                  //.header(HttpHeaders.AUTHORIZATION,"")
                  .contentType(MediaType.APPLICATION_JSON)
          .content(convertObjectToJsonString(userDto))
          .accept(MediaType.APPLICATION_JSON)).andDo(print())
          .andExpect(status().isOk()).andExpect(jsonPath("$.name").exists());


}
@Test
public  void getAllUsersTest() throws Exception {
    UserDto userDto1=new UserDto("1","pallavi Tejas Yeola","pallavi@gmail.com",
            "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");
    UserDto userDto2=new UserDto("2"," Tejas Yeola","pallavi@gmail.com",
            "paLLavi05","male","I am software Engineer with updated Technology","xyz.jpg");
    UserDto userDto3=new UserDto("3","kalyani","pallavi@gmail.com",
            "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");
    UserDto userDto4=new UserDto("4","Harshal","pallavi@gmail.com",
            "paLLavi05","male","I am software Engineer with updated Technology","xyz.jpg");

    PageableResponse pageableResponse=new PageableResponse<>();
    pageableResponse.setContent(Arrays.asList(userDto1,userDto2,userDto3,userDto4));

    pageableResponse.setLastpage(false);
    pageableResponse.setTotalElements(100l);
    pageableResponse.setTotalPages(1000);
    pageableResponse.setPageSize(10);
    pageableResponse.setPageNumber(1);

    when(userService.getAllUser(anyInt(), anyInt(), anyString(), anyString())).thenReturn( pageableResponse);

    mockMvc.perform(get("/users/User/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
}

    // Converting User object to Json i.e. String form, becoz Json data is always in String form
    private String convertObjectToJsonString(Object user){

        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
   @Test
    public void deleteUserTest()
    {
        String userId="";
       userService.deleteuser(userId);
       verify(userService, times(1)).deleteuser(userId);
    }
@Test
public void getUserByIdTest() throws Exception {

    String userid="1";
    UserDto userDto=new UserDto("1","pallavi Tejas Yeola","pallavi@gmail.com",
            "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");
    when(userService.getUserById(Mockito.any())).thenReturn(userDto);
    mockMvc.perform(get("/users/"+userid).contentType(MediaType.APPLICATION_JSON).
            content(convertObjectToJsonString(userDto)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isFound());
}
@Test
public void getUserByEmailTest() throws Exception {
String email="pallavi@gmail.com";
    UserDto userDto=new UserDto("1","pallavi Tejas Yeola","pallavi@gmail.com",
            "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");
Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(userDto);
mockMvc.perform(get("/users/email/"+email).contentType(MediaType.APPLICATION_JSON)
        .contentType(convertObjectToJsonString(userDto)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
}
@Test
public void searchUserTest() throws Exception {
  String keyword="p";
    UserDto userDto=new UserDto("1","pallavi Tejas Yeola","pallavi@gmail.com",
            "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");
    UserDto userDto2=new UserDto("2"," Tejas Yeola","pallavi@gmail.com",
            "paLLavi05","male","I am software Engineer with updated Technology","xyz.jpg");
    UserDto userDto3=new UserDto("3","pinki","pallavi@gmail.com",
            "paLLavi05","female","I am software Engineer with updated Technology","xyz.jpg");

    Mockito.when(userService.SearchUser(Mockito.anyString())).thenReturn(Arrays.asList(userDto,userDto2,userDto3));
  mockMvc.perform(get("/users/search/"+keyword).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
}

/*public  void   uploadImageTest() throws Exception {
   String imageUploadPath;
    MultipartFile image = null;
    String userId="";
    when(fileService.UploadImage(Mockito.any(),Mockito.anyString())).thenReturn(String.valueOf(image));

    String uploadImage = null;
    ImageResponse imageResponse=ImageResponse.builder()
            .imageName(uploadImage).message("Image Added Successfully")
            .success(true).status(HttpStatus.CREATED).build();

    MediaType MultipartFile = "image";
    mockMvc.perform(post("/users/image/"+userId).contentType(MultipartFile).accept(MultipartFile)).andDo(print()).andExpect(status().isCreated());*/

//public void serveUploadImageTest()

}
