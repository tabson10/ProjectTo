package com.example.project.domain.security;

import com.example.project.domain.User;
import lombok.Data;

import javax.persistence.*;

@Entity
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

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
