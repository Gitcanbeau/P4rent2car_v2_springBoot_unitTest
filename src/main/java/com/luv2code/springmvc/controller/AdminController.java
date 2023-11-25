package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.requestmodels.AddCarRequest;
import com.luv2code.springmvc.service.AdminService;
import com.luv2code.springmvc.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/car/quantity")
    public void increaseCarQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long carId) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.increaseCarQuantity(carId);
    }

    @PutMapping("/secure/decrease/car/quantity")
    public void decreaseCarQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long carId) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.decreaseCarQuantity(carId);
    }

    @PostMapping("/secure/add/car")
    public void postCar(@RequestHeader(value="Authorization") String token,
                         @RequestBody AddCarRequest addCarRequest) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.postCar(addCarRequest);
    }

    @DeleteMapping("/secure/delete/car")
    public void deleteCar(@RequestHeader(value="Authorization") String token,
                           @RequestParam Long carId) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.deleteCar(carId);
    }

}












