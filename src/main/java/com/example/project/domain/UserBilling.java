package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "dane_do_faktury")
public class UserBilling {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_adres")
    private int id;

    @Column(name = "imie_nazwisko")
    private String userBillingName;

    @Column(name = "miasto")
    private String userBillingCity;

    @Column(name = "wojewodztwo")
    private String userBillingVoivodeship;

    @Column(name = "ulica")
    private String userBillingStreet;

    @Column(name = "kod_pocztowy")
    private String userBillingPostcode;

    @Column(name = "nr_domu")
    private String userBillingHouseNumber;

    @Column(name = "nr_mieszkania")
    private String userBillingApartmentNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private UserPayment userPayment;
}
