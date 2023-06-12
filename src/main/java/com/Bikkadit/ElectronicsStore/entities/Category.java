package com.Bikkadit.ElectronicsStore.entities;

import lombok.*;

import javax.persistence.Column;
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
public class Category extends CustomFields{
      @Id
      @Column(name="id")
       private String categoryId;

     @Column(name="Category_title",length=60,nullable = false)
      private  String  title;

      @Column(name="Category_Descri",length=60)
      private  String desciption;

    private  String coverImage;
}
