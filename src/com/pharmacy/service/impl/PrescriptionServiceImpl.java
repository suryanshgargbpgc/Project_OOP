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
 * Updated by: Student
 * Date: 11/10/2023
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
            prescription.setImagePath(prescriptionFile.getAbsolutePath());
            
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
        
        // Mark as verified
        prescription.setVerified(true);
        prescription.setVerificationComments("Verified by system");
        
        // Create sample medicines
        Medicine med1 = new Medicine("MED1001", "Loratadine", 12.99, false);
        med1.setInfo("Antihistamine for allergy relief");
        med1.setType("Allergy");
        med1.setCount(50);
        
        Medicine med2 = new Medicine("MED1002", "Fluticasone", 24.99, true);
        med2.setInfo("Nasal spray for allergies");
        med2.setType("Allergy");
        med2.setCount(30);
        
        // Add medicines to prescription
        prescription.addMedicine(med1);
        prescription.addMedicine(med2);
        
        // Add dosage information
        prescription.addMedicineWithDosage(med1, "10mg once daily", 30);
        prescription.addMedicineWithDosage(med2, "2 sprays each nostril daily", 30, "Use in the morning");
        
        // Add instructions
        prescription.addInstructions(
            "Take medications as directed",
            "Avoid known allergens",
            "Stay hydrated",
            "Call if symptoms worsen"
        );
        
        // Store the prescription
        prescriptions.put(prescription.getPrescriptionId(), prescription);
        
        // Create another sample prescription
        Prescription prescription2 = new Prescription();
        prescription2.setPrescriptionId("RX123457");
        prescription2.setCustomerId("CUST100");
        prescription2.setDoctorId("DOC101");
        prescription2.setPatientName("John Smith");
        prescription2.setDoctorName("Dr. Robert Chen");
        prescription2.setDiagnosis("Mild Hypertension");
        prescription2.setIssueDate(new Date());
        
        // Set expiry date (90 days from now for chronic condition)
        Date expiryDate2 = new Date();
        expiryDate2.setTime(expiryDate2.getTime() + 90L * 24 * 60 * 60 * 1000);
        prescription2.setExpiryDate(expiryDate2);
        
        // Mark as verified
        prescription2.setVerified(true);
        prescription2.setVerificationComments("Verified by pharmacist");
        
        // Create sample medicine
        Medicine med3 = new Medicine("MED1003", "Lisinopril", 18.50, true);
        med3.setInfo("ACE inhibitor for blood pressure");
        med3.setType("Cardiovascular");
        med3.setCount(90);
        
        // Add medicine to prescription
        prescription2.addMedicine(med3);
        
        // Add dosage information
        prescription2.addMedicineWithDosage(med3, "10mg once daily with food", 90, "Morning preferred");
        
        // Add instructions
        prescription2.addInstructions(
            "Take medication daily with food",
            "Monitor blood pressure regularly",
            "Maintain low sodium diet",
            "Exercise moderately for 30 minutes daily"
        );
        
        // Store the prescription
        prescriptions.put(prescription2.getPrescriptionId(), prescription2);
        
        logger.info("Demo data initialized with " + prescriptions.size() + " prescriptions");
    }
} 