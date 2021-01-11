package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "adres_wysylki")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_adres")
    private int id;

    @Column(name = "imie_nazwisko")
    private String shippingAddressName;

    @Column(name = "miasto")
    private String shippingAddressCity;

    @Column(name = "wojewodztwo")
    private String shippingAddressVoivodeship;

    @Column(name = "ulica")
    private String shippingAddressStreet;

    @Column(name = "kod_pocztowy")
    private String shippingAddressPostcode;

    @Column(name = "nr_domu")
    private String shippingAddressHouseNumber;

    @Column(name = "nr_mieszkania")
    private String shippingAddressApartmentNumber;

    @ManyToOne
    @JoinColumn(name = "ID_UZYTKOWNIKA")
    private User user;
}
