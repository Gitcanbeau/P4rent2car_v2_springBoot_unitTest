package com.luv2code.springmvc;

import com.luv2code.springmvc.dao.CarRepository;
import com.luv2code.springmvc.dao.CheckoutRepository;
import com.luv2code.springmvc.dao.HistoryRepository;
import com.luv2code.springmvc.dao.PaymentRepository;
import com.luv2code.springmvc.entity.Car;
import com.luv2code.springmvc.entity.Checkout;
import com.luv2code.springmvc.entity.History;
import com.luv2code.springmvc.requestmodels.AddCarRequest;
import com.luv2code.springmvc.service.CarService;
import com.luv2code.springmvc.service.AdminService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class AdminServiceTest {

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
    @Autowired
    private AdminService adminServ;

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
    public void increaseCarQuantity() throws Exception{

        Optional<Car> car1before = carRepo.findById( (long) 1);
        int beforeAmountAvailable=car1before.get().getAmountsAvailable();
        adminServ.increaseCarQuantity((long) 1);
        Optional<Car> car1after = carRepo.findById( (long) 1);
        int afterAmountAvailable=car1after.get().getAmountsAvailable();
        assertTrue(beforeAmountAvailable+1==afterAmountAvailable,"should return true");
    }

    @Test
    public void decreaseCarQuantity()throws Exception{
        Optional<Car> car1before = carRepo.findById( (long) 1);
        int beforeAmountAvailable=car1before.get().getAmountsAvailable();
        adminServ.decreaseCarQuantity((long) 1);
        Optional<Car> car1after = carRepo.findById( (long) 1);
        int afterAmountAvailable=car1after.get().getAmountsAvailable();
        assertTrue(beforeAmountAvailable-1==afterAmountAvailable,"should return true");
    }

    @Test
    public void postCar()throws Exception{
        AddCarRequest addCarRequest=new AddCarRequest("2024 Toyota Hybrid","Toyota","This is hybrid", 10, "Hybrid","imgPlaceholder");
        adminServ.postCar(addCarRequest);
        Pageable pageable=PageRequest.of(0,20);
        Page<Car> car=carRepo.findByCategory("Hybrid",pageable);
        List<Car> carContent=car.getContent();
        assertTrue(carServ.isCarPresentCheck((long)3), "message: id is set as automically increment");
        assertFalse(carContent.isEmpty(),"message: hybrid should be added, should return true, empty is false");
    }

    @Test
    public void deleteCar() throws Exception{
//        assertTrue(carServ.isCarPresentCheck((long)1));
//        adminServ.deleteCar( (long) 2);
//        assertFalse(carServ.isCarPresentCheck((long)1));
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
