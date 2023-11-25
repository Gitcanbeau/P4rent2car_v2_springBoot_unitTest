package com.luv2code.springmvc.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "history")
@Data
public class History {

    public History(){}

    public History(String userEmail, String checkoutDate, String returnedDate, String model,
                   String company, String description, String img) {
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnedDate = returnedDate;
        this.model = model;
        this.company = company;
        this.description = description;
        this.img = img;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="checkout_date")
    private String checkoutDate;

    @Column(name="returned_date")
    private String returnedDate;

    @Column(name="model")
    private String model;

    @Column(name="company")
    private String company;

    @Column(name="description")
    private String description;

    @Column(name="img")
    private String img;
}












