package com.luv2code.springmvc;

import com.luv2code.springmvc.entity.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class rent2carApplication {

	public static void main(String[] args) {
		SpringApplication.run(rent2carApplication.class, args);
	}

	@Bean
	@Scope(value = "prototype")
	Car getCar() {
		return new Car();
	}

	@Bean
	@Scope(value = "prototype")
	Checkout getCheckout() {
		return new Checkout();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("history")
	History getHistory() {
		return new History();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("message")
	Message getMessage() {
		return new Message();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("payment")
	Payment getPayment() {
		return new Payment();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("payment")
	Review getReview() {
		return new Review();
	}

}
