package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.CategoryService;
import com.Bikkadit.ElectronicsStore.dtos.CategoryDto;
import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.dtos.UserDto;
import com.Bikkadit.ElectronicsStore.entities.Category;
import com.Bikkadit.ElectronicsStore.entities.User;
import com.Bikkadit.ElectronicsStore.exceptions.ResourceNotFoundException;
import com.Bikkadit.ElectronicsStore.helper.ForPagination;
import com.Bikkadit.ElectronicsStore.repositories.CategoryRepository;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ModelMapper mapper;
    @Value("${user.profile.image.paths}")
    private String imageUploadPath;
    private static  final Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("Request proceed to create Category in Persistence Layer");

      String categoryId = UUID.randomUUID().toString();
      categoryDto.setCategoryId(categoryId);
   Category category = this.mapper.map(categoryDto, Category.class);
    Category category1 = categoryRepo.save(category);
      CategoryDto newCategory = mapper.map(category1, CategoryDto.class);
        logger.info("Category record saved Successfully in database");
        return newCategory;

    }

    @Override
    public CategoryDto UpdateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Request proceed to update User in Persistence Layer with userId:{}",categoryId);
      Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
       category.setTitle(categoryDto.getTitle());
        category.setDesciption(categoryDto.getDesciption());
      category.setCoverImage(categoryDto.getCoverImage());

       Category updatedCategory = categoryRepo.save(category);
        logger.info("Category Updated Successfully in database with categoryId:{}",categoryId);
        return this.mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Request proceed to Delete User in Persistence Layer with categoryId:{}",categoryId);
     Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));


        String fullPath= imageUploadPath + category.getCoverImage();

        try{
            Path path = Paths.get(fullPath);

            Files.delete(path);
        } catch (IOException e)
        {
            throw  new RuntimeException(e);
        }
        categoryRepo.delete(category);
        logger.info("User Deleted Successfully in Database with categoryId:{}",categoryId);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDto getCategoryById(String categoryId) {
        logger.info("Request proceed to get Category in Persistence Layer with categoryId:{}",categoryId);
       Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("ategory", "categoryId", categoryId));
        logger.info("User Get from DataBase with userId:{}",categoryId);
        return mapper.map(category,CategoryDto.class);
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Request proceed  in Persistance Layer to get All Category Record From Database");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //Sort sort = Sort.by(sortBy)ascending();//only for SortBy
        Pageable pageable= (Pageable) PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> allCategory = categoryRepo.findAll(pageable);
logger.info("Get All Category Records From Database Successfully");
PageableResponse<CategoryDto> pageableResponse = ForPagination.getPageableResponse(allCategory,CategoryDto.class);
        return pageableResponse;
    }

    /**
     *
     * @param keyword
     * @return
     */
    @Override
    public List<CategoryDto> SearchCategory(String keyword) {
        logger.info("Request proceed  in Persistence Layer to get User using keyword:{}",keyword);
      List<Category> Category = categoryRepo.findByTitleContaining(keyword);
  List<CategoryDto> categoryDtos = Category.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        logger.info("Get All Category From Database using keyword");
        return categoryDtos;
    }
}
