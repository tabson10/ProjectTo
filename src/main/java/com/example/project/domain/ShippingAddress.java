package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;

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

    @OneToOne
    private Order order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public String getShippingAddressVoivodeship() {
        return shippingAddressVoivodeship;
    }

    public void setShippingAddressVoivodeship(String shippingAddressVoivodeship) {
        this.shippingAddressVoivodeship = shippingAddressVoivodeship;
    }

    public String getShippingAddressStreet() {
        return shippingAddressStreet;
    }

    public void setShippingAddressStreet(String shippingAddressStreet) {
        this.shippingAddressStreet = shippingAddressStreet;
    }

    public String getShippingAddressPostcode() {
        return shippingAddressPostcode;
    }

    public void setShippingAddressPostcode(String shippingAddressPostcode) {
        this.shippingAddressPostcode = shippingAddressPostcode;
    }

    public String getShippingAddressHouseNumber() {
        return shippingAddressHouseNumber;
    }

    public void setShippingAddressHouseNumber(String shippingAddressHouseNumber) {
        this.shippingAddressHouseNumber = shippingAddressHouseNumber;
    }

    public String getShippingAddressApartmentNumber() {
        return shippingAddressApartmentNumber;
    }

    public void setShippingAddressApartmentNumber(String shippingAddressApartmentNumber) {
        this.shippingAddressApartmentNumber = shippingAddressApartmentNumber;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
