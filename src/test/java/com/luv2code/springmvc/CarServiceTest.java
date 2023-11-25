package com.luv2code.springmvc;



import com.luv2code.springmvc.dao.CarRepository;
import com.luv2code.springmvc.dao.CheckoutRepository;
import com.luv2code.springmvc.dao.HistoryRepository;
import com.luv2code.springmvc.dao.PaymentRepository;
import com.luv2code.springmvc.entity.Car;
import com.luv2code.springmvc.entity.Checkout;
import com.luv2code.springmvc.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.luv2code.springmvc.service.CarService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class CarServiceTest {
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private CheckoutRepository checkoutRepo;

    @Autowired
    private HistoryRepository historyRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private CarService carServ;

    @Value("${sql.script.create.car1}")
    private String sqlAddCar1;

    @Value("${sql.script.create.car2}")
    private String sqlAddCar2;

    @Value("${sql.script.create.checkout1}")
    private String sqlAddCheckout1;

    @Value("${sql.script.create.checkout2}")
    private String sqlAddCheckout2;

    @Value("${sql.script.create.checkout3}")
    private String sqlAddCheckout3;

    @Value("${sql.script.create.history}")
    private String sqlAddHistory;

    @Value("${sql.script.create.message1}")
    private String sqlAddMessage1;

    @Value("${sql.script.create.payment}")
    private String sqlAddPayment;

    @Value("${sql.script.create.review}")
    private String sqlAddReview;

    @Value("${sql.script.delete.car}")
    private String sqlDeleteCar;

    @Value("${sql.script.delete.checkout}")
    private String sqlDeleteCheckout;

    @Value("${sql.script.delete.history}")
    private String sqlDeleteHistory;

    @Value("${sql.script.delete.message}")
    private String sqlDeleteMessage;

    @Value("${sql.script.delete.payment}")
    private String sqlDeletePayment;

    @Value("${sql.script.delete.review}")
    private String sqlDeleteReview;


    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddCar1);
        jdbc.execute(sqlAddCar2);
        jdbc.execute(sqlAddCheckout1);
        jdbc.execute(sqlAddCheckout2);
        jdbc.execute(sqlAddCheckout3);
        jdbc.execute(sqlAddHistory);
        jdbc.execute(sqlAddMessage1);
        jdbc.execute(sqlAddPayment);
        jdbc.execute(sqlAddReview);
    }

    @Test
    public void isCarNullCheck() {

        assertTrue(carServ.isCarPresentCheck((long)1), "@BeforeTransaction creates student : return true");
        assertTrue(carServ.isCarPresentCheck((long)2), "@BeforeTransaction creates student : return true");
        assertFalse(carServ.isCarPresentCheck((long)0), "No car should have 0 id : return false");
    }

    @Test
    public void checkoutCar(){

    }

    @Test
    public void checkoutCarByUser(){
        boolean res1 = carServ.checkoutCarByUser("user1@email.com", (long)1);
        boolean res2 = carServ.checkoutCarByUser("user1@email.com", (long)2);
        boolean res3 = carServ.checkoutCarByUser("user3@email.com", (long)1);
        boolean res4 = carServ.checkoutCarByUser("user2@email.com", (long)2);
        assertTrue(res1,"message: should return true, this info is inserted into checkout");
        assertFalse(res2,"message: should return false, user1 doesn't use car3");
        assertFalse(res3,"message: should return false, user3 doesn't use car1");
        assertTrue(res4,"message: should return true, this info is inserted into checkout");
    }

    @Test
    public void currentLoansCount(){

    }

    @Test
    public void returnCar() throws Exception{
        Optional<Car> beforeReturn=carRepo.findById((long)1);
        Integer beforeReturnAmountAvailable=beforeReturn.get().getAmountsAvailable();

        carServ.returnCar("user1@email.com",(long)1);
        Optional<Car> afterReturn=carRepo.findById((long)1);
        Integer afterReturnAmountAvailable=afterReturn.get().getAmountsAvailable();

        Pageable pageable = PageRequest.of(0, 20);
        Page<History> history=historyRepo.findByUserEmail("user1@email.com", pageable);
        assertTrue(beforeReturnAmountAvailable+1==afterReturnAmountAvailable,"yes, quantity should be increased by 1 after returning");
        assertFalse(carServ.isCheckoutPresentCheck("user1@email.com",(long)1),"after return, the checkout log is moved to payment log and the checkout log is deleted, therefore, getById should be null");
    }

    @Test
    public void renewLoan() throws Exception{
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Checkout checkout1=checkoutRepo.findByUserEmailAndCarId("user1@email.com",(long) 1);
        Date previousDate1=sdFormat.parse(checkout1.getReturnDate());
        carServ.renewLoan("user1@email.com",(long) 1);
        Date updatedDate1=sdFormat.parse(checkout1.getReturnDate());
        System.out.println(previousDate1);
        System.out.println(updatedDate1);
        assertTrue(updatedDate1.compareTo(previousDate1)==0,
                "The original returnDate has passed, you cannot renew the loan, " +
                        "therefore, updatedDate should be same to previousDate, therefore, true");


        Checkout checkout2=checkoutRepo.findByUserEmailAndCarId("user2@email.com",(long) 2);
        Date previousDate2=sdFormat.parse(checkout2.getReturnDate());
        carServ.renewLoan("user2@email.com",(long) 2);
        Checkout checkout2a=checkoutRepo.findByUserEmailAndCarId("user2@email.com",(long) 2);
        Date updatedDate2= sdFormat.parse(checkout2a.getReturnDate());
        System.out.println(previousDate2);
        System.out.println(updatedDate2);

        assertTrue(updatedDate2.compareTo(previousDate2)==0,
                "The original returnDate+7 is still smaller than original returnDate, you can renew the loan, " +
                        "however, updatedDate should be exactly same to previousDate, therefore, true");

        Checkout checkout3=checkoutRepo.findByUserEmailAndCarId("user3@email.com",(long) 3);
        Date previousDate3=sdFormat.parse(checkout3.getReturnDate());
        carServ.renewLoan("user3@email.com",(long) 3);
        Checkout checkout3a=checkoutRepo.findByUserEmailAndCarId("user3@email.com",(long) 3);
        Date updatedDate3= sdFormat.parse(checkout3a.getReturnDate());
        System.out.println(previousDate3);
        System.out.println(updatedDate3);

        assertTrue(updatedDate3.compareTo(previousDate3)>0,
                "The original returnDate is larger than current local time, you can renew the loan, " +
                        "therefore, updatedDate should be previousDate+7, therefore, true");

    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteCar);
        jdbc.execute(sqlDeleteCheckout);
        jdbc.execute(sqlDeleteHistory);
        jdbc.execute(sqlDeleteMessage);
        jdbc.execute(sqlDeletePayment);
        jdbc.execute(sqlDeleteReview);
    }
}
