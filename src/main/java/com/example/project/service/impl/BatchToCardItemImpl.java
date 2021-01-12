package com.example.project.service.impl;

import com.example.project.domain.CartItem;
import com.example.project.repository.BatchToCardItemRepository;
import com.example.project.service.BatchToCardItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchToCardItemImpl implements BatchToCardItemService {
    @Override
    public void deleteByCartItem(CartItem cartItem) {

    }
}
