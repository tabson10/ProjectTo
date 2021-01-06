package com.example.project.domain.security;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "rola")
public class Role {

    @Id
    @Column(name = "id_rola")
    private int roleId;

    @Column(name = "nazwa")
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles = new HashSet<>();
}
