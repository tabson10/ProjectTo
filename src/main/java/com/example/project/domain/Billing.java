package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "dane_do_faktury_wysylka")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_adres")
    private int id;

    @Column(name = "imie_nazwisko")
    private String billingAddressName;

    @Column(name = "miasto")
    private String billingAddressCity;

    @Column(name = "wojewodztwo")
    private String billingAddressVoivodeship;

    @Column(name = "ulica")
    private String billingAddressStreet;

    @Column(name = "kod_pocztowy")
    private String billingAddressPostcode;

    @Column(name = "nr_domu")
    private String billingAddressHouseNumber;

    @Column(name = "nr_mieszkania")
    private String billingAddressApartmentNumber;

    @OneToOne
    private Order order;
}
