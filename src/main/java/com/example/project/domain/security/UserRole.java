package com.example.project.domain.security;

import com.example.project.domain.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "uzytkownik_rola")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_uzytkownik_rola")
    private int userRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_UZYTKOWNIKA")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rola")
    private Role role;


    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
