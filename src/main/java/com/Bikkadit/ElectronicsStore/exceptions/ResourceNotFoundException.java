package com.Bikkadit.ElectronicsStore.exceptions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter

@NoArgsConstructor

public class ResourceNotFoundException extends RuntimeException
{

@Serial
    private static final long serialVersionUID=1L;
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName,String fieldValue) {
        super(String.format("%s not found With %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


    public ResourceNotFoundException(String s) {
    }
}
//we also create Constructor

