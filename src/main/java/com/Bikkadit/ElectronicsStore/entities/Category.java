package com.Bikkadit.ElectronicsStore.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name="Category_table")
@Entity
public class Category {

    private String categoryId;
    private  String  title;
    private  String desciption;
    private  String coverImage;
}
