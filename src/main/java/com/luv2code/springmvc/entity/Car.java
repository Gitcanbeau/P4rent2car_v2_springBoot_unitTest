package com.luv2code.springmvc.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "car")
@Data

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "rent_price")
    private int rentPrice;

    @Column(name = "amounts")
    private int amounts;

    @Column(name = "amounts_available")
    private int amountsAvailable;

    @Column(name = "category")
    private String category;

    @Column(name = "img")
    private String img;
}
