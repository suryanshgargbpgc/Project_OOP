package com.pharmacy.service.impl;

import com.pharmacy.model.Order;
import com.pharmacy.service.Payment;
import com.pharmacy.exception.PaymentException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PaymentServiceImpl implements Payment, Payment.PaymentAuth {
    

    private Map<String, Object> payments = new HashMap<>();
    private Map<String, String> authCodes = new HashMap<>();
    
    private String generatePaymentId() {
        return "PAY-" + System.currentTimeMillis();
    }
    
    @Override
    public boolean processPayment(Order order, String... paymentDetails) throws PaymentException {
        if (order == null) {
            throw new PaymentException("Order cannot be null");
        }
        
        if (order.getTotal() <= 0) {
            throw new PaymentException("Order amount must be greater than zero", "INVALID_AMOUNT");
        }
        
        if (paymentDetails.length < 2) {
            throw new PaymentException("Insufficient payment details provided", "INSUFFICIENT_DETAILS");
        }
        
        try {
            String paymentId = generatePaymentId();
            
            Map<String, Object> paymentInfo = new HashMap<>();
            paymentInfo.put("orderId", order.getID());
            paymentInfo.put("amount", order.getTotal());
            paymentInfo.put("paymentDate", new Date());
            paymentInfo.put("status", "COMPLETED");
            
            payments.put(paymentId, paymentInfo);
            
            order.setPaid(true);
            
            return true;
        } catch (Exception e) {
            throw new PaymentException("Failed to process payment: " + e.getMessage(),
                                     "PAYMENT_PROCESSING_ERROR", e);
        }
    }
    
    @Override
    public boolean refundPayment(Order order, double amount) throws PaymentException {
        if (order == null) {
            throw new PaymentException("Order cannot be null");
        }
        
        if (!order.getPaid()) {
            throw new PaymentException("Cannot refund an unpaid order", "UNPAID_ORDER");
        }
        
        if (amount <= 0 || amount > order.getTotal()) {
            throw new PaymentException("Invalid refund amount", "INVALID_AMOUNT");
        }
        
        try {
            String refundId = "REF" + System.currentTimeMillis();
            
            Map<String, Object> refundInfo = new HashMap<>();
            refundInfo.put("orderId", order.getID());
            refundInfo.put("refundAmount", amount);
            refundInfo.put("refundDate", new Date());
            refundInfo.put("status", "COMPLETED");
            
            payments.put(refundId, refundInfo);
            
            if (amount >= order.getTotal()) {
                order.setPaid(false);
            }
            
            return true;
        } catch (Exception e) {
            throw new PaymentException("Failed to process refund: " + e.getMessage(),
                                     "REFUND_PROCESSING_ERROR", e);
        }
    }
    
    @Override
    public boolean verifyPaymentStatus(Order order) {
        if (order == null) {
            return false;
        }
        
        return order.getPaid();
    }
    
    @Override
    public String getPaymentReceipt(Order order) {
        if (order == null) {
            return "No order provided";
        }
        
        if (!order.getPaid()) {
            return "Order is not paid";
        }
        
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== PAYMENT RECEIPT ===\n");
        receipt.append("Order ID: ").append(order.getID()).append("\n");
        receipt.append("Date: ").append(new Date()).append("\n");
        receipt.append("Amount: $").append(String.format("%.2f", order.getTotal())).append("\n");
        receipt.append("Payment Method: ").append(order.getPayMethod()).append("\n");
        receipt.append("Customer ID: ").append(order.getCustID()).append("\n");
        receipt.append("Status: PAID\n");
        receipt.append("=====================");
        
        return receipt.toString();
    }
    
    @Override
    public boolean authenticate(String transactionId, String authCode) {
        if (transactionId == null || authCode == null) {
            return false;
        }
        
        String savedAuthCode = authCodes.get(transactionId);
        if (savedAuthCode != null && savedAuthCode.equals(authCode)) {
            authCodes.remove(transactionId);
            return true;
        }
        
        return false;
    }
    
    @Override
    public String sendAuthCode(String transactionId, String phoneNumber) {
        if (transactionId == null || phoneNumber == null) {
            return null;
        }
        
        String authCode = String.format("%06d", (int)(Math.random() * 1000000));

        authCodes.put(transactionId, authCode);
        
        return authCode; // In a real implementation, we wouldn't return this
    }
    

    public boolean validateCardDetails(String cardNumber, String expiryDate, String cvv) {

        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            return false;
        }
        
        if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        
        if (cvv == null || !cvv.matches("\\d{3}")) {
            return false;
        }
        
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        
        return (sum % 10 == 0);
    }
} 