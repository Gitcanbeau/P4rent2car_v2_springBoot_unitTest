package com.luv2code.springmvc.dao;

import com.luv2code.springmvc.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {


    Page<Car> findByModelContaining(@RequestParam("model") String model, Pageable pageable);

    Page<Car> findByCategory(@RequestParam("category") String category, Pageable pageable);

    @Query("select o from Car o where o.id in :id")
    List<Car> findCarsByCarIds (@Param("id") List<Long> carId);
}
