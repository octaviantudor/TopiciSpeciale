package com.unibuc.shoppingcart.repository;

import com.unibuc.shoppingcart.domain.entity.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartItemsRepository extends JpaRepository<ShoppingCartItem, Long> {
}
