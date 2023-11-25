package com.luv2code.springmvc.dao;

import com.luv2code.springmvc.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByCarId(@RequestParam("car_id") Long carId,
                              Pageable pageable);

    Review findByUserEmailAndCarId(String userEmail, Long carId);

    @Modifying
    @Query("delete from Review o where o.carId in: car_id")
    void deleteAllByCarId(@Param("car_id") Long carId);
}
