package com.luv2code.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springmvc.models2.CollegeStudent;
import com.luv2code.springmvc.models2.MathGrade;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class CarControllerTest {
    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbc;



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


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


    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;


    @BeforeAll
    public static void setup() {

        request = new MockHttpServletRequest();

        request.setParameter("firstname", "Chad");

        request.setParameter("lastname", "Darby");

        request.setParameter("emailAddress", "chad.darby@luv2code_school.com");
    }

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
    public void currentLoansCount(){

    }

    @Test
    public void checkoutCarByUser(){}

    @Test
    public void checkoutCar(){

    }

    @Test
    public void returnCar(){

    }

    @Test
    public void renewLoan(){

    }
}
