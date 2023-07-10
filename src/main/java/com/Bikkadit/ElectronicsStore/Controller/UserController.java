package com.Bikkadit.ElectronicsStore.Controller;
import com.Bikkadit.ElectronicsStore.Services.FileService;
import com.Bikkadit.ElectronicsStore.Services.UserService;
import com.Bikkadit.ElectronicsStore.dtos.ImageResponse;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.helper.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.paths}")
    private String imageUploadPath;
    /**
     * @apiNote This Ai is Used to Create New User
     * @param userDto used to pass User details
     * @return UserDto
     */
    @PostMapping("/CreateUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        log.info("Request Entering to create User In Service:{}",userDto);
        UserDto userDto1=userService.createUser(userDto);
        log.info("User Created Successfully ");
        return  new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    /**
     * @apiNote  This Api is used To Update User With userId
     * @param userDto UserDto Object
     * @param userId UserId
     * @return userDto
     */
    @PutMapping("/updatedUser/{userId}")
    public ResponseEntity<UserDto> Updateuser(@Valid @RequestBody UserDto userDto,
                                              @PathVariable String userId
                                              )
    {
        log.info("Request Entering Into Service To Update User With userId:{}",userId);
        UserDto userDto1= userService.UpdateUser(userDto,userId);

        log.info("User Updated Successfully With userId:{}",userId);
 log.info("User Updated Successfully With Id:{}",userId);

        return new ResponseEntity<>(userDto1,HttpStatus.OK);

    }

    /**
     * @apiNote This Api is used To Get User By UserId
     * @param userId is used to get User
     * @return User
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId)
    {
        log.info("Request Entering into Service Layer to get User With userId:{}",userId);
      UserDto userDto=  userService.getUserById(userId);

   log.info("Get user Successfully With userId:{}",userId);
log.info("Get user Successfully With Id:{}",userId);
      return new ResponseEntity<>(userDto,HttpStatus.FOUND);
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/User")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
        @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue =AppConstant.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY_USER, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)

    {
        log.info("Request To get All users from Database");
       PageableResponse<UserDto> users= userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);
        log.info("Get All Users Successfully");
        return  new ResponseEntity<>(users,HttpStatus.OK);
    }


    /**
     * @apiNote This Api is used to delete User With userId
     * @param userId used to delete user
     * @return Message With Successfully deleted or not
     */
    @DeleteMapping("/{userId}")
    public  ResponseEntity<String> deleteUser(@PathVariable String userId)
   // public  ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId)
    {
        log.info("Request Entering Into service to delete user with userId:{}",userId);
        userService.deleteuser(userId);
        log.info("Record Deleted Successfully With UserId:{}",userId);
       // return new ResponseEntity<ApiResponse>(new ApiResponse("Record Deleted Successfully",true,HttpStatus.OK),HttpStatus.OK);
        return new ResponseEntity<>(AppConstant.USER_DELETE,HttpStatus.OK);
    }

    /**
     * @apiNote This Api is used to Get user By Email
     * @param email is used to find Particular user
     * @return User with Email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable  String email)
    {
        log.info("Request Entering Into Service Layer to get User With Email:{}",email);
        log.info("User get Successfully with Email:{}",email);
      return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    /**
     * @apiNote This Api is used to get users By using keyword
     * @param keyword is used to find Users
     * @return UserDto With Keyword
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword)
    {
        log.info("Request to get User By particular keyword");
        return  new ResponseEntity<>(userService.SearchUser(keyword),HttpStatus.OK);
    }

  @PostMapping("/image/{userId}")  //upload Image
public ResponseEntity<ImageResponse> uploadImage(@RequestPart ("uplaodImage")MultipartFile image,
                                                 @PathVariable String userId) throws IOException {

      String uploadImage = fileService.UploadImage(image, imageUploadPath);

      UserDto userDto= userService.getUserById(userId);

      userDto.setImageName(uploadImage);
    userService.UpdateUser(userDto,userId);

      ImageResponse imageResponse=ImageResponse.builder()
              .imageName(uploadImage).message("Image Added Successfully")
              .success(true).status(HttpStatus.CREATED).build();

    return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
}
    //servr Image
    @GetMapping("/image/{userId}")
    public void serveUploadImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
      UserDto userDto= userService.getUserById(userId);
      log.info("User image :{}",userDto.getImageName());
      InputStream resource = fileService.getResource(imageUploadPath, userDto.getImageName());

      response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
