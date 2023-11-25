package com.luv2code.springmvc.requestmodels;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {


    private double rating;

    private Long carId;

    private Optional<String> reviewDescription;
    private ReviewRequest(){}
    public ReviewRequest(double rating, Long carId, Optional<String> reviewDescription){
        this.rating=rating;
        this.carId=carId;
        this.reviewDescription=reviewDescription;
    }
}
