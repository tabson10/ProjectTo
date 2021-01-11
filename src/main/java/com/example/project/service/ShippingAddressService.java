package com.example.project.service;

import com.example.project.domain.ShippingAddress;
import com.example.project.domain.UserShipping;

public interface ShippingAddressService {
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
