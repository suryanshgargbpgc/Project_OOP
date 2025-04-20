package com.pharmacy.service;

import com.pharmacy.model.Order;
import com.pharmacy.exception.PaymentException;

/**
 * Payment interface defines methods for processing payments
 */
public interface Payment {
    
    /**
     * Process a payment for an order
     * 
     * @param order The order to process payment for
     * @param paymentDetails Payment details (card number, expiry, etc.)
     * @return true if payment was successful, false otherwise
     * @throws PaymentException if there's an error processing the payment
     */
    boolean processPayment(Order order, String... paymentDetails) throws PaymentException;
    
    /**
     * Refund a payment for an order
     * 
     * @param order The order to refund
     * @param amount The amount to refund (can be partial)
     * @return true if refund was successful, false otherwise
     * @throws PaymentException if there's an error processing the refund
     */
    boolean refundPayment(Order order, double amount) throws PaymentException;
    
    /**
     * Verify payment status of an order
     * 
     * @param order The order to verify payment for
     * @return true if payment has been completed, false otherwise
     */
    boolean verifyPaymentStatus(Order order);
    
    /**
     * Get payment receipt for an order
     * 
     * @param order The order to get receipt for
     * @return String representation of the receipt
     */
    String getPaymentReceipt(Order order);
    
    // Nested interface for payment authentication
    interface PaymentAuth {
        /**
         * Authenticate a payment transaction
         * 
         * @param transactionId The payment transaction ID
         * @param authCode Authentication code or OTP
         * @return true if authentication is successful, false otherwise
         */
        boolean authenticate(String transactionId, String authCode);
        
        /**
         * Send authentication code to the customer
         * 
         * @param customerId The customer ID
         * @param contactMethod Contact method (email, phone, etc.)
         * @return The transaction ID for authenticating the payment
         */
        String sendAuthCode(String customerId, String contactMethod);
    }
} 