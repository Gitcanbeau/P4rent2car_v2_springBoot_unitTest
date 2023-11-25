package com.luv2code.springmvc.requestmodels;

import lombok.Data;

@Data
public class AdminQuestionRequest {

    private Long id;

    private String response;

    public AdminQuestionRequest(){}
    public AdminQuestionRequest(Long id, String response) {
        this.id = id;
        this.response = response;
    }
}
