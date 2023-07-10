package com.Bikkadit.ElectronicsStore.util;

import com.Bikkadit.ElectronicsStore.entities.BaseResponse;
import com.Bikkadit.ElectronicsStore.helper.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponseType {

    public static <T> T errorResponse(Integer statuscode,String message)
    {
        BaseResponse<?> baseResponse=new BaseResponse<>(statuscode,message,null);
        return (T) new ResponseEntity<BaseResponse<?>>( baseResponse, HttpStatus.OK);
    }
    public static <T> T errorResponse(String message)
    {
        BaseResponse<T> baseResponse=new BaseResponse<>(AppConstant.API_FAIL_CODE,message,null);
        return (T) new ResponseEntity<BaseResponse<T>>( baseResponse, HttpStatus.OK);
    }
    public static <T> T successfulResponse(Integer statuscode,Object data)
    {
        BaseResponse<Object> baseResponse=new BaseResponse<>(statuscode," Date Get Successfully ",data);
        return (T) new ResponseEntity<BaseResponse<Object>>( baseResponse, HttpStatus.OK);
    }
    public static <T> T successfulResponse(Object data)
    {
        BaseResponse<Object> baseResponse=new BaseResponse<>(AppConstant.API_SUCCESSFUL_CODE,"Date Get Successfully ",data);
        return (T) new ResponseEntity<BaseResponse<Object>>( baseResponse, HttpStatus.OK);
    }
}
