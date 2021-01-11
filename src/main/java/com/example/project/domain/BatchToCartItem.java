package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "partia_koszyk")
public class BatchToCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "partia_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "pozycja_w_koszyku_id")
    private CartItem cartItem;
}
