package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.UserService;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration

@ExtendWith(MockitoExtension.class)
public class UserControllTest {

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private  UserController userController;
    private User user;
private  UserDto userDto;
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

        UserDto userDto=new UserDto("");
        userDto.setName("pallavi Tejas Yeola");
        userDto.setAbout("I am software Engineer with updated Technology");
        userDto.setGender("female");
        userDto.setEmail("pallavi@gmail.com");
        userDto.setPassword("paLLavi05");
        userDto.setImageName("xyz.jpg");

     Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);




        //ObjectMapper mapper=new ObjectMapper();

        //String writeValueAsString = mapper.writeValueAsString(userDto);   //convert java object to Json(String)

        mockMvc.perform(post("/users/CreateUser")                      //perform post
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
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
}
