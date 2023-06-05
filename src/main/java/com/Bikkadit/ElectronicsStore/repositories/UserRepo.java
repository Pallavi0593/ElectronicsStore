package com.Bikkadit.ElectronicsStore.repositories;

import com.Bikkadit.ElectronicsStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {

    @Override
    Optional<User> findById(String s);

    Optional<User> findByEmail(String Email);
User findByEmailAndPassword(String Email,String Password);
  List<User> findByNameContaining(String keyword);
}
