package com.pharmacy.dao;

import com.pharmacy.model.Prescription;
import java.util.List;

public interface PrescriptionDAO {
    
    /**
     * Save a prescription to the database
     * 
     * @param prescription The prescription to save
     * @return The saved prescription with potentially updated ID
     */
    Prescription save(Prescription prescription);
    
    /**
     * Find a prescription by its ID
     * 
     * @param prescriptionId The ID of the prescription to find
     * @return The found prescription, or null if not found
     */
    Prescription findById(String prescriptionId);
    
    /**
     * Find all prescriptions for a customer
     * 
     * @param customerId The ID of the customer
     * @return List of prescriptions for the customer
     */
    List<Prescription> findByCustomerId(String customerId);
    
    /**
     * Find all prescriptions issued by a doctor
     * 
     * @param doctorId The ID of the doctor
     * @return List of prescriptions issued by the doctor
     */
    List<Prescription> findByDoctorId(String doctorId);
    
    /**
     * Update an existing prescription
     * 
     * @param prescription The prescription to update
     * @return The updated prescription
     */
    Prescription update(Prescription prescription);
    
    /**
     * Delete a prescription by its ID
     * 
     * @param prescriptionId The ID of the prescription to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteById(String prescriptionId);
    
    /**
     * Find all valid (not expired) prescriptions for a customer
     * 
     * @param customerId The ID of the customer
     * @return List of valid prescriptions
     */
    List<Prescription> findValidPrescriptionsByCustomerId(String customerId);
    
    /**
     * Find all verified prescriptions for a customer
     * 
     * @param customerId The ID of the customer
     * @return List of verified prescriptions
     */
    List<Prescription> findVerifiedPrescriptionsByCustomerId(String customerId);
    
    /**
     * Get the total number of prescriptions in the system
     * 
     * @return The total count
     */
    int getTotalCount();
} 