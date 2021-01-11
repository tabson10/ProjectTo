package com.example.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity(name = "pozycja_w_koszyku")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "ilosc")
    private int qty;

    @Column(name = "suma")
    private BigDecimal subtotal;

    @OneToOne
    private Batch batch;

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<BatchToCartItem> batchToCartItemList;

    @ManyToOne
    @JoinColumn(name = "koszyk_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "zamowienie_id")
    private Order order;
}
