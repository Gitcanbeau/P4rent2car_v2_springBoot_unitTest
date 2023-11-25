package com.luv2code.springmvc.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Data
public class Message {

    public Message(){}

    public Message(String model, String question) {
        this.model = model;
        this.question = question;
    }

    public Message(Long id, String userEmail, String model, String question, String adminEmail, String response, boolean closed) {
        this.id = id;
        this.userEmail = userEmail;
        this.model = model;
        this.question = question;
        this.adminEmail = adminEmail;
        this.response = response;
        this.closed = closed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="model")
    private String model;

    @Column(name="question")
    private String question;

    @Column(name="admin_email")
    private String adminEmail;

    @Column(name="response")
    private String response;

    @Column(name="closed")
    private boolean closed;
}













