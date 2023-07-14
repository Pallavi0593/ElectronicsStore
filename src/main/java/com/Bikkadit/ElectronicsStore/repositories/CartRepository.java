package com.Bikkadit.ElectronicsStore.repositories;

import com.Bikkadit.ElectronicsStore.entities.Cart;
import com.Bikkadit.ElectronicsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {


    Optional<Cart> findByUser(User user);
}
