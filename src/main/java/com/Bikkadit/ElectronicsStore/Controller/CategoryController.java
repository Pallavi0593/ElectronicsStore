package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.CategoryService;
import com.Bikkadit.ElectronicsStore.Services.FileService;
import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.ImageResponse;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.helper.ApiResponse;
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
@RequestMapping("/Categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Value("${user.profile.image.paths.category}")
    private String imageUploadPathcategory;

    @Autowired
    private FileService fileService;
    /**
     * @apiNote  This Api is used To create New Category
     * @param categoryDto
     * @return new Category
     */
    @PostMapping("/CreateCategory")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        log.info("Request Entering to create Category In Service:{}",categoryDto);
      CategoryDto category = categoryService.createCategory(categoryDto);
        log.info("New Category record Created Successfully ");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @apiNote This Api is used to Update Category
     * @param categoryDto
     * @param categoryId
     * @return Updated Category
     */
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> Updateuser(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId)
    {
        log.info("Request Entering Into Service To Update User With categoryId:{}",categoryId);
     CategoryDto categoryDto1 = categoryService.UpdateCategory(categoryDto, categoryId);
        log.info("User Updated Successfully With Id:{}",categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);

    }

    /**
     * @apiNote  This Api is used to get Category  By ID
     * @param categoryId
     * @return Category By Id
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId)
    {
        log.info("Request Entering into Service Layer to get User With categoryId:{}",categoryId);
   CategoryDto categoryById = categoryService.getCategoryById(categoryId);
        log.info("Get user Successfully With Id:{}",categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.FOUND);
    }

    /**
     * @apiNote This Method Is used For Get  All Category
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return CategoryDto in Pageable Format
     */

    @GetMapping("/User")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue =AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY_CATEGORY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir)

    {
        log.info("Request To get All Category from Database");
      PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        log.info("Get All Categories Successfully");
        return  new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

    /**
     * @apiNote  This Api is used to delete Category
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    //public  ResponseEntity<String> deleteCategory(@PathVariable String categoryId)
     public  ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId)
    {
        log.info("Request Entering Into service to delete user with categoryId:{}",categoryId);
        categoryService.deleteCategory(categoryId);
        log.info("Record Deleted Successfully With UserId:{}",categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Record Deleted Successfully",true,HttpStatus.OK),HttpStatus.OK);
        //return new ResponseEntity<>(AppConstant.USER_DELETE,HttpStatus.OK);
    }

    /**
     * @apiNote  This Api is used to Search Category using Keyword
     * @param keyword
     * @return  Category conatins specific Keyword
     */

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchUser(@PathVariable String keyword)
    {
        log.info("Request to get User By particular keyword");
        return  new ResponseEntity<>(categoryService.SearchCategory(keyword),HttpStatus.OK);
    }

    @PostMapping("/image/{categoryId}")  //upload Image
    public ResponseEntity<ImageResponse> uploadImage(@RequestPart ("uplaodImage") MultipartFile image,
                                                     @PathVariable String categoryId) throws IOException {

        String uploadImage = fileService.UploadImage(image, imageUploadPathcategory);

      CategoryDto categoryDto= categoryService.getCategoryById(categoryId);

        categoryDto.setCoverImage(uploadImage);
      CategoryDto categoryDto1 = categoryService.UpdateCategory(categoryDto, categoryId);


        ImageResponse imageResponse=ImageResponse.builder()
                .imageName(uploadImage).message("Image Added Successfully")
                .success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
    //servr Image
    @GetMapping("/image/{categoryId}")
    public void serveUploadImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        log.info("User image :{}",categoryDto.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPathcategory, categoryDto.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
