package com.example.project.service;

import com.example.project.domain.UserPayment;

import java.util.Optional;

public interface UserPaymentService {
    Optional<UserPayment> findById(int id);

    void removeById(UserPayment userPayment);
}
