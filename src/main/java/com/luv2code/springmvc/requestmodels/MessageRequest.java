package com.luv2code.springmvc.requestmodels;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class MessageRequest {

    private String model;

    private String question;

    public MessageRequest(){

    }
    public MessageRequest(String model, String question){
        this.model=model;
        this.question=question;
    }
}
