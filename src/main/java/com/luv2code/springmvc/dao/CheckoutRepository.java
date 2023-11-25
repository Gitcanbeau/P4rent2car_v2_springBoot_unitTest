package com.luv2code.springmvc.dao;

import com.luv2code.springmvc.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndCarId(String userEmail, Long carId);

    List<Checkout> findCarsByUserEmail(@RequestParam("user_email") String userEmail);

    @Modifying
    @Query("delete from Checkout o where o.carId in :car_id")
    void deleteAllByCarId(@Param("car_id") Long carId);
}
