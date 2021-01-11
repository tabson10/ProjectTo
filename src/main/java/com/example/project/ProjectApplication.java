package com.example.project;

import com.example.project.domain.User;
import com.example.project.domain.security.Role;
import com.example.project.domain.security.UserRole;
import com.example.project.service.UserService;
import com.example.project.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        /* Creating test user
        User user1 = new User();
        user1.setFirstName("Test");
        user1.setLastName("test");
        user1.setUsername("testowy");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("test"));
        user1.setEmail("projectto1@outlook.com");
        Set<UserRole> userRoles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setName("ROLE_USER");
        userRoles.add(new UserRole(user1, role1));

        userService.createUser(user1, userRoles);
         */
    }

}