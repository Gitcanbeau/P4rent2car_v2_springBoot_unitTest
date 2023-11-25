package com.luv2code.springmvc;

import com.luv2code.springmvc.dao.CarRepository;
import com.luv2code.springmvc.dao.CheckoutRepository;
import com.luv2code.springmvc.dao.HistoryRepository;
import com.luv2code.springmvc.dao.PaymentRepository;
import com.luv2code.springmvc.dao.MessageRepository;
import com.luv2code.springmvc.entity.Car;
import com.luv2code.springmvc.entity.Checkout;
import com.luv2code.springmvc.entity.History;
import com.luv2code.springmvc.entity.Message;
import com.luv2code.springmvc.requestmodels.AdminQuestionRequest;
import com.luv2code.springmvc.requestmodels.MessageRequest;
import com.luv2code.springmvc.service.MessagesService;
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

public class MessagesServiceTest {

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
    private MessageRepository messageRepo;

    @Autowired
    private MessagesService messagesServ;

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

    @Value("${sql.script.create.message2}")
    private String sqlAddMessage2;

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
        jdbc.execute(sqlAddMessage2);
        jdbc.execute(sqlAddPayment);
        jdbc.execute(sqlAddReview);
    }




    @Test
    public void postMessage(){
        MessageRequest messageRequest=new MessageRequest("2025 Toyota SUV","Do we have 2025 Toyota SUV?");
        messagesServ.postMessage(messageRequest,"user4@email.com");
        assertTrue(messagesServ.isMessagePresentCheck("user4@email.com"),"message: just added, should return true");
        assertFalse(messagesServ.isMessagePresentCheck("user3@email.com"),"message: dont add at all, should return false");
    }

    @Test
    public void putMessage() throws Exception{
        Message shouldBeAdded=new Message((long)2, "user2@email.com", "2024 Toyota SUV", "Is this question pannel","admin2@email.com", "Welcome to question pannel", true);
        Pageable pageable=PageRequest.of(0,20);
        Page<Message> beforeAnswer=messageRepo.findByClosed(false,pageable);
        List<Message> beforeAnswerContent= beforeAnswer.getContent();
        assertFalse(beforeAnswerContent.contains(shouldBeAdded),"message: before answering question, should be false");
        AdminQuestionRequest adminQuestionRequest=new AdminQuestionRequest((long) 2, "Welcome to question pannel");
        messagesServ.putMessage(adminQuestionRequest,"admin2@email.com");
        Page<Message> afterAnswer=messageRepo.findByClosed(true,pageable);
        List<Message> afterAnswerContent= afterAnswer.getContent();
        assertTrue(afterAnswerContent.contains(shouldBeAdded),"message: after answering question, should be true");

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
