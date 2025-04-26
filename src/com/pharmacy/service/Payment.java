package com.pharmacy.service;

import com.pharmacy.model.Order;
import com.pharmacy.exception.PaymentException;

public interface Payment {

    boolean processPayment(Order order, String... paymentDetails) throws PaymentException;

    boolean refundPayment(Order order, double amount) throws PaymentException;

    boolean verifyPaymentStatus(Order order);

    String getPaymentReceipt(Order order);
    

    interface PaymentAuth {

        boolean authenticate(String transactionId, String authCode);

        String sendAuthCode(String customerId, String contactMethod);
    }
} 