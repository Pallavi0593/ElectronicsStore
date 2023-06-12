package com.Bikkadit.ElectronicsStore.dtos;

import com.Bikkadit.ElectronicsStore.util.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto  extends CustomFieldDto{


    private String categoryId;

    @NotNull
    @Size(min=10,max=20,message ="Min size for category title is 10")
    private  String  title;

    @NotNull
    @Size(min=10,max=20,message ="Min size for category title is 10")
    private  String desciption;

    @ImageNameValid
    private  String coverImage;
}
