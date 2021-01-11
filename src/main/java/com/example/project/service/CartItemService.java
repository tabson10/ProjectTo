package com.example.project.service;

import com.example.project.domain.CartItem;
import com.example.project.domain.ShoppingCart;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);
}
