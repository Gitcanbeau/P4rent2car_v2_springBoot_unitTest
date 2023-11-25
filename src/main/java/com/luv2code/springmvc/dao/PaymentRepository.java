package com.luv2code.springmvc.dao;

import com.luv2code.springmvc.entity.History;
import com.luv2code.springmvc.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByUserEmail(@RequestParam("user_email") String userEmail);
    Page<Payment> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);
}