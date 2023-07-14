package com.Bikkadit.ElectronicsStore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch =FetchType.EAGER)
    private Set<Product> products=new HashSet<>();
}
