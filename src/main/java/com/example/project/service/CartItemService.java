package com.example.project.service;

import com.example.project.domain.*;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addBatchToCartItem(Batch batch, User user, int qty);

    Optional<CartItem> findById(int id);

    void removeCartItem(CartItem cartItem);

    CartItem save(CartItem cartItem);

    List<CartItem> findByOrder(Order order);
}
