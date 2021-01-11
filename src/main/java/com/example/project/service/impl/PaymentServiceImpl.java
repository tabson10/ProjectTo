package com.example.project.service.impl;

import com.example.project.domain.Payment;
import com.example.project.domain.UserPayment;
import com.example.project.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
        payment.setCardName(userPayment.getCardName());
        payment.setCardNumber(userPayment.getCardNumber());
        payment.setCvc(userPayment.getCvc());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setHolderName(userPayment.getHolderName());
        payment.setType(userPayment.getType());

        return payment;
    }
}
