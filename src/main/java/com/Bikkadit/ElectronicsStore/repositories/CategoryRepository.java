package com.Bikkadit.ElectronicsStore.repositories;

import com.Bikkadit.ElectronicsStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
