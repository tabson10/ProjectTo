package com.example.project.service;

import com.example.project.domain.*;

import java.util.Optional;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, Billing billing, Payment payment, String shippingMethod, User user);
    Optional<Order> findOne(int id);
}
