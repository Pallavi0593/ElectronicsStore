package com.Bikkadit.ElectronicsStore.exceptions;

import com.Bikkadit.ElectronicsStore.helper.ApiResponse;
import lombok.Builder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@Builder
@Slf4j

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)    //if particular Resource not found ex user
    public ResponseEntity<ApiResponse> ResourceNotFoundException(ResourceNotFoundException ex)
    {
        log.info("Exception handler Invoked!!");

        //ApiResponse.builder().message(ex.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();
        String message = ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,true,HttpStatus.NOT_FOUND);
        return new  ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);

    }
@ExceptionHandler(MethodArgumentNotValidException.class)          //After validation If fields not fullfill requirements
    public ResponseEntity<Map<String,String>> MethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String,String> map=new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error)->
              {
                 String field= ((FieldError)error).getField();
                 String defaultMessage = error.getDefaultMessage();
                 map.put(field, defaultMessage);
              });
        return new  ResponseEntity<Map<String, String>>(map,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PropertyReferenceException.class)                            //for sorting if we are Giving unknown property then this exception Occures
    public ResponseEntity<ApiResponse> PropertyReferenceException(PropertyReferenceException ex)
    {
        log.info("Exception handler Invoked!!");

        //ApiResponse.builder().message(ex.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();
        String message = ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,true,HttpStatus.NOT_FOUND);
        return new  ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadApiRequestException.class)          //if image which we want to upload  not contains Specific extension .png ,,jpg, .jpeg then then exception occurs
    public ResponseEntity<ApiResponse> BadApiRequest(BadApiRequestException ex)
    {
        log.info("Exception handler Invoked!!");

        String message = ex.getMessage();
        ApiResponse apiResponse =new ApiResponse(message,false,HttpStatus.NOT_FOUND);
        return new  ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);

    }
    //MaxUploadSizeExceededException
    //StringOutOfBoundException    when uploading if we are not select image then we will get this exception

}
