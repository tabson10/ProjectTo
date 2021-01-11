package com.example.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity(name = "partia")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PARTII")
    private int batchId;

    @Column(name = "data_waznosci")
    private Date date;

    @Column(name = "gatunek")
    private String type;

    @Column(name = "cena")
    private String price;

    @Column(name = "ilosc")
    private int quantity;

    @Column(name = "opis", columnDefinition = "text")
    private String description;

    @Column(name = "ID_OWOCU")
    private int fruit;

    @Column(name = "ID_PRODUCENCI")
    private int producer;

    @Column(name = "ID_MAGAZYNU")
    private int warehouse;

    @OneToMany(mappedBy = "batch")
    @JsonIgnore
    private List<BatchToCartItem> bookToCartItemList;
}
