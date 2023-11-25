package com.luv2code.springmvc.requestmodels;

import lombok.Data;

@Data
public class AddCarRequest {

    private String model;

    private String company;

    private String description;

    private int amounts;

    private String category;

    private String img;


    public AddCarRequest(){
    }
    public AddCarRequest(String model, String company, String description, int amounts, String category, String img) {
        this.model = model;
        this.company = company;
        this.description = description;
        this.amounts = amounts;
        this.category = category;
        this.img = img;
    }
}
