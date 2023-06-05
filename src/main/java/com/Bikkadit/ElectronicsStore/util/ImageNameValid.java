package com.Bikkadit.ElectronicsStore.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface  ImageNameValid {

    //error Message
    String message() default "{Invalid Imagine Name}";

    //Represent Group of constraints
    Class<?>[] groups() default { };

    //Additional Information about annotation
    Class<? extends Payload>[] payload() default { };

}
