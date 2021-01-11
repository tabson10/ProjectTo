package com.example.project.service;

import com.example.project.domain.Payment;
import com.example.project.domain.UserPayment;

public interface PaymentService {
    Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
