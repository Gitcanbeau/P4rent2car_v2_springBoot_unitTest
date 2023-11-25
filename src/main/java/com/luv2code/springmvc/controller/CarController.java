package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.entity.Car;
import com.luv2code.springmvc.responsemodels.ShelfCurrentLoansResponse;
import com.luv2code.springmvc.service.CarService;
import com.luv2code.springmvc.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token)
        throws Exception
    {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return carService.currentLoans(userEmail);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return carService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutCarByUser(@RequestHeader(value = "Authorization") String token,
                                      @RequestParam Long carId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return carService.checkoutCarByUser(userEmail, carId);
    }

    @PutMapping("/secure/checkout")
    public Car checkoutCar (@RequestHeader(value = "Authorization") String token,
                              @RequestParam Long carId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return carService.checkoutCar(userEmail, carId);
    }

    @PutMapping("/secure/return")
    public void returnCar(@RequestHeader(value = "Authorization") String token,
                           @RequestParam Long carId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        carService.returnCar(userEmail, carId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String token,
                          @RequestParam Long carId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        carService.renewLoan(userEmail, carId);
    }

}












