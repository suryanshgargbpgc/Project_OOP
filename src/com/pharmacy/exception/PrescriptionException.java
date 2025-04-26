package com.pharmacy.exception;

/**
 * Exception thrown when there's an error with prescriptions
 */
public class PrescriptionException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private String prescriptionId;
    /*commit this  */
    
    /**
     * Default constructor
     */
    public PrescriptionException() {
        super("An error occurred with the prescription");
    }
    
    /**
     * Constructor with error message
     * 
     * @param message The error message
     */
    public PrescriptionException(String message) {
        super(message);
    }
    
    /**
     * Constructor with error message and prescription ID
     * 
     * @param message The error message
     * @param prescriptionId The ID of the prescription that caused the error
     */
    public PrescriptionException(String message, String prescriptionId) {
        super(message);
        this.prescriptionId = prescriptionId;
    }
    
    /**
     * Constructor with error message and cause
     * 
     * @param message The error message
     * @param cause The cause of the exception
     */
    public PrescriptionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructor with error message, prescription ID, and cause
     * 
     * @param message The error message
     * @param prescriptionId The ID of the prescription that caused the error
     * @param cause The cause of the exception
     */
    public PrescriptionException(String message, String prescriptionId, Throwable cause) {
        super(message, cause);
        this.prescriptionId = prescriptionId;
    }
    
    /**
     * Get the prescription ID
     * 
     * @return The prescription ID
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }
    
    /**
     * Set the prescription ID
     * 
     * @param prescriptionId The prescription ID
     */
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
    
    @Override
    public String toString() {
        return "PrescriptionException [prescriptionId=" + prescriptionId + ", message=" + getMessage() + "]";
    }
} 