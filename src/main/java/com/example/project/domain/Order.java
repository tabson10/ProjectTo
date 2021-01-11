package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "zamowienia")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "data_zamowienia")
    private Date orderDate;

    @Column(name = "data_wysylki")
    private Date shippingDate;

    @Column(name = "rodzaj_dostawy")
    private String shippingMethod;

    @Column(name = "status_zamowienia")
    private String orderStatus;

    @Column(name = "cena")
    private BigDecimal orderTotal;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;

    @OneToOne
    private ShippingAddress shippingAddress;

    @OneToOne
    private Payment payment;

    @ManyToOne
    private User user;

}
