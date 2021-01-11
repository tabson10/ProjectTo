package com.example.project.repository;

import com.example.project.domain.CartItem;
import com.example.project.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
