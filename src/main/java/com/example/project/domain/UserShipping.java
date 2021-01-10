package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "adres")
public class UserShipping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_adres")
    private int id;

    @Column(name = "imie_nazwisko")
    private String userShippingName;

    @Column(name = "miasto")
    private String userShippingCity;

    @Column(name = "wojewodztwo")
    private String userShippingVoivodeship;

    @Column(name = "ulica")
    private String userShippingStreet;

    @Column(name = "kod_pocztowy")
    private String userShippingPostcode;

    @Column(name = "nr_domu")
    private String userShippingHouseNumber;

    @Column(name = "nr_mieszkania")
    private String userShippingApartmentNumber;

    @Column(name = "domyslny_adres")
    private boolean defaultAddress;

    @ManyToOne
    @JoinColumn(name = "ID_UZYTKOWNIKA")
    private User user;
}
