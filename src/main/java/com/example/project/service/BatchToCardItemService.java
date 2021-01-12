package com.example.project.service;

import com.example.project.domain.CartItem;
import org.springframework.stereotype.Service;

@Service
public interface BatchToCardItemService {
    void deleteByCartItem(CartItem cartItem);
}
