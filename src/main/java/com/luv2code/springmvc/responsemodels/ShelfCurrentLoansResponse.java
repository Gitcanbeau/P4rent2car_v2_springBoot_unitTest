package com.luv2code.springmvc.responsemodels;

import com.luv2code.springmvc.entity.Car;
import lombok.Data;

@Data
public class ShelfCurrentLoansResponse {

    public ShelfCurrentLoansResponse(Car car, int daysLeft) {
        this.car = car;
        this.daysLeft = daysLeft;
    }

    private Car car;

    private int daysLeft;
}
