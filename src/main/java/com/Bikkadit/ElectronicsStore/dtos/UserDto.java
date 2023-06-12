package com.Bikkadit.ElectronicsStore.dtos;

import com.Bikkadit.ElectronicsStore.util.ImageNameValid;
import lombok.*;



import javax.validation.constraints.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class UserDto extends CustomFieldDto{


    private String userid;

    @Size(min=5,max = 20,message = "Name Must be  5 character")
    private String name;

   @Pattern(regexp ="[a-zA-Z0-9][a-zA-Z0-9-.]+@[a-z]*([.][a-zA-Z]+)+" ,message="length must be 6")
    @Email(message = "mail Address not valid")
    private String email;


    @NotBlank(message = "Password Required")
    @Pattern(regexp ="[a-z][a-zA-Z]*[0-9]+{6}" ,message="length must be 6")
    private String password;


    @Size(min = 4,max = 6,message = "Invalid Gender!!")
    private String gender;

    @NotBlank(message = "Write Something About YourSelf")
    private String about;

    @ImageNameValid
    private String imageName;
}
