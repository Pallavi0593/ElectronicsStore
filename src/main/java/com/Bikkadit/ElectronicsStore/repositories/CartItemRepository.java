package com.Bikkadit.ElectronicsStore.repositories;

import com.Bikkadit.ElectronicsStore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}
