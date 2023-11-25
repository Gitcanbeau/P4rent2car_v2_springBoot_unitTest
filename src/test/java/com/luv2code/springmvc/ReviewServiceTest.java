package com.luv2code.springmvc;

import com.luv2code.springmvc.dao.CarRepository;
import com.luv2code.springmvc.dao.CheckoutRepository;
import com.luv2code.springmvc.dao.HistoryRepository;
import com.luv2code.springmvc.dao.PaymentRepository;
import com.luv2code.springmvc.dao.ReviewRepository;
import com.luv2code.springmvc.entity.Car;
import com.luv2code.springmvc.entity.Checkout;
import com.luv2code.springmvc.entity.History;
import com.luv2code.springmvc.entity.Review;
import com.luv2code.springmvc.requestmodels.ReviewRequest;
import com.luv2code.springmvc.service.ReviewService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("/application-test.properties")
@SpringBootTest

public class ReviewServiceTest {

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
    private ReviewRepository reviewRepo;

    @Autowired
    private ReviewService reviewServ;

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
    public void postReview() throws Exception{
        assertFalse(reviewServ.isReviewPresentCheck("user2@email.com",(long)2),"message: before posing review, should return false");
        ReviewRequest readyToAddReview=new ReviewRequest(4.0, (long)2, Optional.of("This 2024 Toyota SUV is also cool"));
        reviewServ.postReview("user2@email.com",readyToAddReview);

        assertTrue(reviewServ.isReviewPresentCheck("user2@email.com",(long)2),"message: after posting review, should return true");
        assertFalse(reviewServ.isReviewPresentCheck("user2@email.com",(long)3),"message: dont post this review at all, should return false");
    }

    @Test
    public void userReviewListed(){
        assertTrue(reviewServ.userReviewListed("user1@email.com",(long)1),"should be true");
        assertFalse(reviewServ.userReviewListed("user3@email.com",(long)1),"should be false");
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
