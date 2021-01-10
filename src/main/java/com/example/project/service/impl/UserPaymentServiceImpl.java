package com.example.project.service.impl;

import com.example.project.domain.UserPayment;
import com.example.project.repository.UserPaymentRepository;
import com.example.project.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    public Optional<UserPayment> findById(int id) {
        return userPaymentRepository.findById(id);
    }

    @Override
    public void removeById(UserPayment userPayment) {
        userPaymentRepository.delete(userPayment);
    }
}
