package com.Bikkadit.ElectronicsStore.Controller;

import com.Bikkadit.ElectronicsStore.Services.CategoryService;
import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.helper.ApiResponse;
import com.Bikkadit.ElectronicsStore.helper.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * @apiNote  This Api is used To create New Category
     * @param categoryDto
     * @return new Category
     */
    @PostMapping("/CreateCategory")
    public ResponseEntity<CategoryDto> createUser(@Valid @RequestBody CategoryDto categoryDto)
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
    @DeleteMapping("/{userId}")
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
}
