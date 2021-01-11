package com.example.project.service.impl;

import com.example.project.domain.Billing;
import com.example.project.domain.UserBilling;
import com.example.project.service.BillingService;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceImpl implements BillingService {
    @Override
    public Billing setByUserBilling(UserBilling userBilling, Billing billing) {
        billing.setBillingAddressCity(userBilling.getUserBillingCity());
        billing.setBillingAddressStreet(userBilling.getUserBillingStreet());
        billing.setBillingAddressName(userBilling.getUserBillingName());
        billing.setBillingAddressHouseNumber(userBilling.getUserBillingHouseNumber());
        billing.setBillingAddressPostcode(userBilling.getUserBillingPostcode());
        billing.setBillingAddressApartmentNumber(userBilling.getUserBillingApartmentNumber());
        billing.setBillingAddressVoivodeship(userBilling.getUserBillingVoivodeship());
        return billing;
    }
}
