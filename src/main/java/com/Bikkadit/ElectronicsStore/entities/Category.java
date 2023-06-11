package com.Bikkadit.ElectronicsStore.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name="Category_table")
@Entity
public class Category {
@Id
    private String categoryId;
    private  String  title;
    private  String desciption;
    private  String coverImage;
}
