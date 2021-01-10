package com.example.project.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "producenci")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PRODUCENCI")
    private int producerId;

    @Column(name = "imie")
    private String firstNameProducer;

    @Column(name = "nazwisko")
    private String lastNameProducer;

    @Column(name = "opis", columnDefinition = "text")
    private String description;
}
