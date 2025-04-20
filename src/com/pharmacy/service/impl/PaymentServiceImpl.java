package com.pharmacy.service.impl;

import com.pharmacy.model.Order;
import com.pharmacy.service.Payment;
import com.pharmacy.exception.PaymentException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Implementation of Payment interface for processing payments
 */
public class PaymentServiceImpl implements Payment, Payment.PaymentAuth {
    
    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());
    
    // Maps to store payment and authentication data (simulated database)
    private Map<String, Object> payments = new HashMap<>();
    private Map<String, String> authCodes = new HashMap<>();
    
    @Override
    public boolean processPayment(Order order, String... paymentDetails) throws PaymentException {
        if (order == null) {
            throw new PaymentException("Order cannot be null");
        }
        
        if (order.getTotalAmount() <= 0) {
            throw new PaymentException("Order amount must be greater than zero", "INVALID_AMOUNT");
        }
        
        if (paymentDetails.length < 2) {
            throw new PaymentException("Insufficient payment details provided", "INSUFFICIENT_DETAILS");
        }
        
        try {
            // In a real implementation, this would connect to a payment gateway
            String paymentId = generatePaymentId();
            
            // Store payment information
            Map<String, Object> paymentInfo = new HashMap<>();
            paymentInfo.put("orderId", order.getOrderId());
            paymentInfo.put("amount", order.getTotalAmount());
            paymentInfo.put("paymentDate", new Date());
            paymentInfo.put("status", "COMPLETED");
            
            payments.put(paymentId, paymentInfo);
            
            // Update order payment status
            order.setPaid(true);
            
            logger.info("Payment processed successfully for order: " + order.getOrderId());
            return true;
        } catch (Exception e) {
            logger.severe("Error processing payment: " + e.getMessage());
            throw new PaymentException("Failed to process payment: " + e.getMessage(), 
                                     "PAYMENT_PROCESSING_ERROR", e);
        }
    }
    
    @Override
    public boolean refundPayment(Order order, double amount) throws PaymentException {
        if (order == null) {
            throw new PaymentException("Order cannot be null");
        }
        
        if (!order.isPaid()) {
            throw new PaymentException("Cannot refund an unpaid order", "UNPAID_ORDER");
        }
        
        if (amount <= 0 || amount > order.getTotalAmount()) {
            throw new PaymentException("Invalid refund amount", "INVALID_AMOUNT");
        }
        
        try {
            // In a real implementation, this would connect to a payment gateway
            String refundId = "REF" + System.currentTimeMillis();
            
            // Store refund information
            Map<String, Object> refundInfo = new HashMap<>();
            refundInfo.put("orderId", order.getOrderId());
            refundInfo.put("refundAmount", amount);
            refundInfo.put("refundDate", new Date());
            refundInfo.put("status", "COMPLETED");
            
            payments.put(refundId, refundInfo);
            
            // If full refund, update order payment status
            if (amount >= order.getTotalAmount()) {
                order.setPaid(false);
            }
            
            logger.info("Refund processed successfully for order: " + order.getOrderId());
            return true;
        } catch (Exception e) {
            logger.severe("Error processing refund: " + e.getMessage());
            throw new PaymentException("Failed to process refund: " + e.getMessage(), 
                                     "REFUND_PROCESSING_ERROR", e);
        }
    }
    
    @Override
    public boolean verifyPaymentStatus(Order order) {
        if (order == null) {
            return false;
        }
        
        // In a real implementation, this would query the payment gateway
        return order.isPaid();
    }
    
    @Override
    public String getPaymentReceipt(Order order) {
        if (order == null) {
            return "No order provided";
        }
        
        if (!order.isPaid()) {
            return "Order is not paid";
        }
        
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== PAYMENT RECEIPT ===\n");
        receipt.append("Order ID: ").append(order.getOrderId()).append("\n");
        receipt.append("Date: ").append(new Date()).append("\n");
        receipt.append("Amount: $").append(String.format("%.2f", order.getTotalAmount())).append("\n");
        receipt.append("Payment Method: ").append(order.getPaymentMethod()).append("\n");
        receipt.append("Customer ID: ").append(order.getCustomerId()).append("\n");
        receipt.append("Status: PAID\n");
        receipt.append("=====================");
        
        return receipt.toString();
    }
    
    @Override
    public boolean authenticate(String transactionId, String authCode) {
        if (transactionId == null || authCode == null) {
            return false;
        }
        
        // Check if the auth code matches what was sent
        String savedAuthCode = authCodes.get(transactionId);
        if (savedAuthCode != null && savedAuthCode.equals(authCode)) {
            // Remove the auth code after successful authentication
            authCodes.remove(transactionId);
            return true;
        }
        
        return false;
    }
    
    @Override
    public String sendAuthCode(String customerId, String contactMethod) {
        if (customerId == null || contactMethod == null) {
            return null;
        }
        
        // Generate a transaction ID and auth code
        String transactionId = "TXN" + System.currentTimeMillis();
        String authCode = generateAuthCode();
        
        // In a real implementation, this would send the auth code via SMS or email
        logger.info("Sending auth code " + authCode + " to customer " + customerId + " via " + contactMethod);
        
        // Store the auth code for later verification
        authCodes.put(transactionId, authCode);
        
        return transactionId;
    }
    
    /**
     * Generate a unique payment ID
     * 
     * @return The generated payment ID
     */
    private String generatePaymentId() {
        return "PAY" + System.currentTimeMillis();
    }
    
    /**
     * Generate a random 6-digit authentication code
     * 
     * @return The generated auth code
     */
    private String generateAuthCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }
} 