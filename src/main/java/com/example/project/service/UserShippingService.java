package com.example.project.service;

import com.example.project.domain.UserShipping;

import java.util.Optional;

public interface UserShippingService {
    Optional<UserShipping> findById(int id);

    void removeById(UserShipping userShipping);
}
