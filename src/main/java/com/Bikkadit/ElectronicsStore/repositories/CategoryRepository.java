package com.Bikkadit.ElectronicsStore.repositories;

import com.Bikkadit.ElectronicsStore.entities.Category;
import com.Bikkadit.ElectronicsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {

    List<Category> findByTitleContaining(String keyword);
}
