package com.pharmacy.exception;

public class PaymentException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private String errorCode;

    public PaymentException() {
        super("An error occurred during payment processing");
        this.errorCode = "PAYMENT_ERROR";
    }

    public PaymentException(String message) {
        super(message);
        this.errorCode = "PAYMENT_ERROR";
    }
    
    /**
     * Constructor with error message and error code
     * @param message The error message
     * @param errorCode The error code
     */
    public PaymentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * Constructor with error message and cause
     * @param message The error message
     * @param cause The cause of the exception
     */
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "PAYMENT_ERROR";
    }
    
    /**
     * Constructor with error message, error code, and cause
     * @param message The error message
     * @param errorCode The error code
     * @param cause The cause of the exception
     */
    public PaymentException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    @Override
    public String toString() {
        return "PaymentException [errorCode=" + errorCode + ", message=" + getMessage() + "]";
    }
} 