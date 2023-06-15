package com.Bikkadit.ElectronicsStore.repositories;

import com.Bikkadit.ElectronicsStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {

}
