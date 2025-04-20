package com.pharmacy.service.impl;

import com.pharmacy.model.Prescription;
import com.pharmacy.model.Medicine;
import com.pharmacy.service.PrescriptionService;
import com.pharmacy.exception.PrescriptionException;
import com.pharmacy.dao.PrescriptionDAO;
import com.pharmacy.dao.MedicineDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Implementation of PrescriptionService interface
 */
public class PrescriptionServiceImpl implements PrescriptionService {
    
    private static final Logger logger = Logger.getLogger(PrescriptionServiceImpl.class.getName());
    
    // Simulated DAO objects (would be injected in a real application)
    private PrescriptionDAO prescriptionDAO;
    private MedicineDAO medicineDAO;
    
    // In-memory storage for prescriptions (simulated database)
    private Map<String, Prescription> prescriptions = new HashMap<>();
    
    public PrescriptionServiceImpl() {
        // Initialize demo data
        initializeDemoData();
    }
    
    @Override
    public Prescription uploadPrescription(String customerId, File prescriptionFile) throws PrescriptionException {
        if (customerId == null || customerId.isEmpty()) {
            throw new PrescriptionException("Customer ID cannot be null or empty");
        }
        
        if (prescriptionFile == null || !prescriptionFile.exists()) {
            throw new PrescriptionException("Prescription file is invalid or does not exist");
        }
        
        try {
            // Create a new prescription
            Prescription prescription = new Prescription();
            prescription.setPrescriptionId(UUID.randomUUID().toString());
            prescription.setCustomerId(customerId);
            prescription.setIssueDate(new Date());
            
            // Set expiry date (30 days from now)
            Date expiryDate = new Date();
            expiryDate.setTime(expiryDate.getTime() + 30L * 24 * 60 * 60 * 1000);
            prescription.setExpiryDate(expiryDate);
            
            // In a real application, this would:
            // 1. Upload the image to a storage service
            // 2. Process the image with OCR to extract prescription details
            // 3. Store the path to the uploaded image
            
            // Simulate file processing
            FileInputStream fis = new FileInputStream(prescriptionFile);
            byte[] fileBytes = new byte[(int) prescriptionFile.length()];
            fis.read(fileBytes);
            fis.close();
            
            // Store the file path (in a real app, this would be a URL to the stored image)
            prescription.setPrescriptionImagePath(prescriptionFile.getAbsolutePath());
            
            // Add the prescription to our storage
            prescriptions.put(prescription.getPrescriptionId(), prescription);
            
            logger.info("Prescription uploaded successfully: " + prescription.getPrescriptionId());
            return prescription;
            
        } catch (IOException e) {
            logger.severe("Error uploading prescription: " + e.getMessage());
            throw new PrescriptionException("Failed to process prescription file: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Prescription verifyPrescription(String prescriptionId, String verifiedByUserId, String comments) throws PrescriptionException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new PrescriptionException("Prescription ID cannot be null or empty");
        }
        
        Prescription prescription = prescriptions.get(prescriptionId);
        if (prescription == null) {
            throw new PrescriptionException("Prescription not found", prescriptionId);
        }
        
        // Update verification status
        prescription.setVerified(true);
        prescription.setVerificationComments(comments);
        
        logger.info("Prescription verified: " + prescriptionId + " by user: " + verifiedByUserId);
        return prescription;
    }
    
    @Override
    public Prescription getPrescription(String prescriptionId) throws PrescriptionException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new PrescriptionException("Prescription ID cannot be null or empty");
        }
        
        Prescription prescription = prescriptions.get(prescriptionId);
        if (prescription == null) {
            throw new PrescriptionException("Prescription not found", prescriptionId);
        }
        
        return prescription;
    }
    
    @Override
    public List<Prescription> getCustomerPrescriptions(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Prescription> customerPrescriptions = new ArrayList<>();
        for (Prescription prescription : prescriptions.values()) {
            if (prescription.getCustomerId().equals(customerId)) {
                customerPrescriptions.add(prescription);
            }
        }
        
        return customerPrescriptions;
    }
    
    @Override
    public List<Prescription> getValidPrescriptions(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Prescription> validPrescriptions = new ArrayList<>();
        Date currentDate = new Date();
        
        for (Prescription prescription : prescriptions.values()) {
            if (prescription.getCustomerId().equals(customerId) && 
                prescription.isVerified() && 
                !prescription.isExpired()) {
                validPrescriptions.add(prescription);
            }
        }
        
        return validPrescriptions;
    }
    
    @Override
    public boolean isValidPrescriptionForMedicine(String prescriptionId, String medicineId) throws PrescriptionException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new PrescriptionException("Prescription ID cannot be null or empty");
        }
        
        if (medicineId == null || medicineId.isEmpty()) {
            throw new PrescriptionException("Medicine ID cannot be null or empty");
        }
        
        Prescription prescription = prescriptions.get(prescriptionId);
        if (prescription == null) {
            throw new PrescriptionException("Prescription not found", prescriptionId);
        }
        
        // Check if prescription is valid and contains the medicine
        return prescription.isVerified() && 
               !prescription.isExpired() && 
               prescription.containsMedicine(medicineId);
    }
    
    @Override
    public Prescription createDigitalPrescription(Prescription prescription) throws PrescriptionException {
        if (prescription == null) {
            throw new PrescriptionException("Prescription cannot be null");
        }
        
        if (prescription.getDoctorId() == null || prescription.getDoctorId().isEmpty()) {
            throw new PrescriptionException("Doctor ID cannot be null or empty");
        }
        
        if (prescription.getCustomerId() == null || prescription.getCustomerId().isEmpty()) {
            throw new PrescriptionException("Customer ID cannot be null or empty");
        }
        
        // Generate a new ID if not provided
        if (prescription.getPrescriptionId() == null || prescription.getPrescriptionId().isEmpty()) {
            prescription.setPrescriptionId(UUID.randomUUID().toString());
        }
        
        // Set current date as issue date if not provided
        if (prescription.getIssueDate() == null) {
            prescription.setIssueDate(new Date());
        }
        
        // Set expiry date if not provided (30 days from issue date)
        if (prescription.getExpiryDate() == null) {
            Date expiryDate = new Date(prescription.getIssueDate().getTime());
            expiryDate.setTime(expiryDate.getTime() + 30L * 24 * 60 * 60 * 1000);
            prescription.setExpiryDate(expiryDate);
        }
        
        // Digital prescriptions are automatically verified
        prescription.setVerified(true);
        
        // Store the prescription
        prescriptions.put(prescription.getPrescriptionId(), prescription);
        
        logger.info("Digital prescription created: " + prescription.getPrescriptionId());
        return prescription;
    }
    
    @Override
    public boolean deletePrescription(String prescriptionId) throws PrescriptionException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new PrescriptionException("Prescription ID cannot be null or empty");
        }
        
        if (!prescriptions.containsKey(prescriptionId)) {
            throw new PrescriptionException("Prescription not found", prescriptionId);
        }
        
        // Remove the prescription
        prescriptions.remove(prescriptionId);
        logger.info("Prescription deleted: " + prescriptionId);
        
        return true;
    }
    
    /**
     * Initialize demo data for testing
     */
    private void initializeDemoData() {
        // Create a sample prescription
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId("RX123456");
        prescription.setCustomerId("CUST100");
        prescription.setDoctorId("DOC100");
        prescription.setPatientName("John Smith");
        prescription.setDoctorName("Dr. Jane Wilson");
        prescription.setDiagnosis("Seasonal Allergies");
        prescription.setIssueDate(new Date());
        
        // Set expiry date (30 days from now)
        Date expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime() + 30L * 24 * 60 * 60 * 1000);
        prescription.setExpiryDate(expiryDate);
        
        // Add some sample medications
        Prescription.Medication medication1 = new Prescription.Medication(
            "Cetirizine", "10mg", "Once daily", "7 days", "Take in the morning"
        );
        Prescription.Medication medication2 = new Prescription.Medication(
            "Fluticasone", "50mcg", "Twice daily", "14 days", "2 sprays in each nostril"
        );
        
        prescription.addMedication(medication1);
        prescription.addMedication(medication2);
        
        // Add instructions
        prescription.addInstructions(
            "Avoid alcohol while taking this medication",
            "Stay hydrated",
            "Return for follow-up if symptoms don't improve in 7 days"
        );
        
        // Verify the prescription
        prescription.setVerified(true);
        
        // Store the prescription
        prescriptions.put(prescription.getPrescriptionId(), prescription);
    }
} 