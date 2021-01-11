package com.example.project.service;

import com.example.project.domain.Batch;
import com.example.project.domain.CartItem;
import com.example.project.domain.ShoppingCart;
import com.example.project.domain.User;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addBatchToCartItem(Batch batch, User user, int qty);
}
