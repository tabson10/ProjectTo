package com.example.project.service;

import com.example.project.domain.Billing;
import com.example.project.domain.UserBilling;
import org.springframework.stereotype.Service;

public interface BillingService {
    Billing setByUserBilling(UserBilling userBilling, Billing billing);
}
