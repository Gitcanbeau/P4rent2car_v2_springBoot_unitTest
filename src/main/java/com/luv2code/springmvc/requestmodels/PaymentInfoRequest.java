package com.luv2code.springmvc.requestmodels;

import lombok.Data;

@Data
public class PaymentInfoRequest {

    private int amount;
    private String currency;
    private String receiptEmail;


    public PaymentInfoRequest(){}
    public PaymentInfoRequest(int amount, String currency, String receiptEmail) {
        this.amount = amount;
        this.currency = currency;
        this.receiptEmail = receiptEmail;
    }
}
