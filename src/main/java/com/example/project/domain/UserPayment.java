package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "uzytkownik_platnosc")
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_UZYTKOWNIK_PLATNOSC")
    private int id;

    @Column(name = "typ")
    private String type;

    @Column(name = "nazwa_karty")
    private String cardName;

    @Column(name = "numer_karty")
    private String cardNumber;

    @Column(name = "miesiac")
    private int expiryMonth;

    @Column(name = "rok")
    private int expiryYear;

    @Column(name = "cvc")
    private int cvc;

    @Column(name = "imie_nazwisko_wlasciciela")
    private String holderName;

    @Column(name = "domyslna_platnosc")
    private boolean defaultPayment;

    @ManyToOne
    @JoinColumn(name = "ID_UZYTKOWNIKA")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userPayment")
    private UserBilling userBilling;
}
