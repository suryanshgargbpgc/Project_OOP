package com.pharmacy.service;

import com.pharmacy.model.Prescription;
import com.pharmacy.exception.PrescriptionException;
import java.io.File;
import java.util.List;

/**
 * Interface for prescription-related services
 */
public interface PrescriptionService {
    
    /**
     * Upload a prescription image
     * 
     * @param customerId The ID of the customer
     * @param prescriptionFile The prescription image file
     * @return The created prescription object
     * @throws PrescriptionException if there's an error uploading the prescription
     */
    Prescription uploadPrescription(String customerId, File prescriptionFile) throws PrescriptionException;
    
    /**
     * Verify a prescription
     * 
     * @param prescriptionId The ID of the prescription to verify
     * @param verifiedByUserId The ID of the user (usually a pharmacist) who verified the prescription
     * @param comments Verification comments
     * @return The verified prescription
     * @throws PrescriptionException if there's an error verifying the prescription
     */
    Prescription verifyPrescription(String prescriptionId, String verifiedByUserId, String comments) throws PrescriptionException;
    
    /**
     * Get a prescription by ID
     * 
     * @param prescriptionId The ID of the prescription to retrieve
     * @return The prescription object
     * @throws PrescriptionException if the prescription is not found
     */
    Prescription getPrescription(String prescriptionId) throws PrescriptionException;
    
    /**
     * Get all prescriptions for a customer
     * 
     * @param customerId The ID of the customer
     * @return List of prescriptions
     */
    List<Prescription> getCustomerPrescriptions(String customerId);
    
    /**
     * Get all valid (not expired) prescriptions for a customer
     * 
     * @param customerId The ID of the customer
     * @return List of valid prescriptions
     */
    List<Prescription> getValidPrescriptions(String customerId);
    
    /**
     * Check if a prescription is valid for a specific medicine
     * 
     * @param prescriptionId The ID of the prescription
     * @param medicineId The ID of the medicine
     * @return true if the prescription is valid for the medicine, false otherwise
     * @throws PrescriptionException if there's an error checking the prescription
     */
    boolean isValidPrescriptionForMedicine(String prescriptionId, String medicineId) throws PrescriptionException;
    
    /**
     * Create a digital prescription (by a doctor)
     * 
     * @param prescription The prescription to create
     * @return The created prescription
     * @throws PrescriptionException if there's an error creating the prescription
     */
    Prescription createDigitalPrescription(Prescription prescription) throws PrescriptionException;
    
    /**
     * Delete a prescription
     * 
     * @param prescriptionId The ID of the prescription to delete
     * @return true if deleted successfully, false otherwise
     * @throws PrescriptionException if there's an error deleting the prescription
     */
    boolean deletePrescription(String prescriptionId) throws PrescriptionException;
} 