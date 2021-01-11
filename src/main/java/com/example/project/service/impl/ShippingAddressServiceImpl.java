package com.example.project.service.impl;

import com.example.project.domain.ShippingAddress;
import com.example.project.domain.UserShipping;
import com.example.project.service.ShippingAddressService;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
    @Override
    public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
        shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
        shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
        shippingAddress.setShippingAddressStreet(userShipping.getUserShippingStreet());
        shippingAddress.setShippingAddressPostcode(userShipping.getUserShippingPostcode());
        shippingAddress.setShippingAddressVoivodeship(userShipping.getUserShippingVoivodeship());
        shippingAddress.setShippingAddressApartmentNumber(userShipping.getUserShippingApartmentNumber());
        shippingAddress.setShippingAddressHouseNumber(userShipping.getUserShippingHouseNumber());

        return shippingAddress;
    }
}
