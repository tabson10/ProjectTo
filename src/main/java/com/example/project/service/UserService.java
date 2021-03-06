package com.example.project.service;

import com.example.project.domain.User;
import com.example.project.domain.UserBilling;
import com.example.project.domain.UserPayment;
import com.example.project.domain.UserShipping;
import com.example.project.domain.security.PasswordResetToken;
import com.example.project.domain.security.UserRole;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    Optional<User> findById(int id);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);

    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

    void setUserDefaultPayment(int id, User user);

    void updateUserShipping(UserShipping userShipping, User user);

    void setUserDefaultShippingAddress(int id, User user);
}
