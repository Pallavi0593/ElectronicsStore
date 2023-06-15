package com.Bikkadit.ElectronicsStore.repositories;

import com.Bikkadit.ElectronicsStore.dtos.PageableResponse;
import com.Bikkadit.ElectronicsStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

     PageableResponse<List<Product>> findByTitleContaining(String subtitle) ;

    PageableResponse<List<Product>> findByLive();

   }


