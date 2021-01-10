package com.example.project.service.impl;

import com.example.project.domain.UserShipping;
import com.example.project.repository.UserShippingRepository;
import com.example.project.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    private UserShippingRepository userShippingRepository;

    public Optional<UserShipping> findById(int id) {
        return userShippingRepository.findById(id);
    }

    @Override
    public void removeById(UserShipping userShipping) {
        userShippingRepository.delete(userShipping);
    }
}
