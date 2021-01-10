package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "owoc")
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_OWOCU")
    private int id;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "ilosc")
    private int quantity;

    @Column(name = "opis", columnDefinition = "text")
    private String description;

}
