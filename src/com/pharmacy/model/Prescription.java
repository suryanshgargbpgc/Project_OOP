package com.pharmacy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Prescription class represents a medical prescription issued by a doctor
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String prescriptionId;
    private String customerId;
    private String doctorId;
    private String patientName;
    private String doctorName;
    private Date issueDate;
    private Date expiryDate;
    private String diagnosis;
    private List<Medicine> medicines;
    private List<String> instructions;
    private boolean isVerified;
    private String prescriptionImagePath; // Path to uploaded prescription image
    private String verificationComments;
    
    // Inner class for medicine dosage instructions
    public class MedicineDosage implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private Medicine medicine;
        private String dosage; // E.g., "1 tablet 3 times a day after meals"
        private int duration; // In days
        private String specialInstructions;
        
        public MedicineDosage(Medicine medicine, String dosage, int duration) {
            this.medicine = medicine;
            this.dosage = dosage;
            this.duration = duration;
            this.specialInstructions = "";
        }
        
        public MedicineDosage(Medicine medicine, String dosage, int duration, String specialInstructions) {
            this.medicine = medicine;
            this.dosage = dosage;
            this.duration = duration;
            this.specialInstructions = specialInstructions;
        }
        
        // Getters and Setters
        public Medicine getMedicine() {
            return medicine;
        }
        
        public void setMedicine(Medicine medicine) {
            this.medicine = medicine;
        }
        
        public String getDosage() {
            return dosage;
        }
        
        public void setDosage(String dosage) {
            this.dosage = dosage;
        }
        
        public int getDuration() {
            return duration;
        }
        
        public void setDuration(int duration) {
            this.duration = duration;
        }
        
        public String getSpecialInstructions() {
            return specialInstructions;
        }
        
        public void setSpecialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
        }
        
        @Override
        public String toString() {
            return medicine.getName() + " - " + dosage + 
                   " for " + duration + " days. " + 
                   (specialInstructions.isEmpty() ? "" : "Note: " + specialInstructions);
        }
    }
    
    // Nested class for Medication (for uploaded prescriptions)
    public static class Medication implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private String dosage;
        private String frequency;
        private String duration;
        private String instructions;
        
        public Medication() {
            this.name = "";
            this.dosage = "";
            this.frequency = "";
            this.duration = "";
            this.instructions = "";
        }
        
        public Medication(String name, String dosage, String frequency, String duration, String instructions) {
            this.name = name;
            this.dosage = dosage;
            this.frequency = frequency;
            this.duration = duration;
            this.instructions = instructions;
        }
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDosage() { return dosage; }
        public void setDosage(String dosage) { this.dosage = dosage; }
        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }
        public String getInstructions() { return instructions; }
        public void setInstructions(String instructions) { this.instructions = instructions; }
    }
    
    private List<MedicineDosage> medicinesDosage;
    private List<Medication> medications;
    
    // Default constructor
    public Prescription() {
        this.prescriptionId = java.util.UUID.randomUUID().toString();
        this.customerId = "";
        this.doctorId = "";
        this.patientName = "";
        this.doctorName = "";
        this.issueDate = new Date();
        this.expiryDate = new Date();
        this.diagnosis = "";
        this.medicines = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.isVerified = false;
        this.prescriptionImagePath = "";
        this.verificationComments = "";
        this.medicinesDosage = new ArrayList<>();
        this.medications = new ArrayList<>();
    }
    
    // Constructor with basic information
    public Prescription(String customerId, String doctorId, String diagnosis) {
        this.prescriptionId = java.util.UUID.randomUUID().toString();
        this.customerId = customerId;
        this.doctorId = doctorId;
        this.patientName = "";
        this.doctorName = "";
        this.issueDate = new Date();
        
        // Set expiry date to 30 days from issue date
        this.expiryDate = new Date();
        this.expiryDate.setTime(this.expiryDate.getTime() + 30L * 24 * 60 * 60 * 1000);
        
        this.diagnosis = diagnosis;
        this.medicines = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.isVerified = false;
        this.prescriptionImagePath = "";
        this.verificationComments = "";
        this.medicinesDosage = new ArrayList<>();
        this.medications = new ArrayList<>();
    }
    
    // Check if the prescription is valid (not expired)
    public boolean isValid() {
        Date currentDate = new Date();
        return isVerified && currentDate.before(expiryDate);
    }
    
    // Add a medicine to the prescription
    public void addMedicine(Medicine medicine) {
        if (!medicines.contains(medicine)) {
            medicines.add(medicine);
        }
    }
    
    // Add a medication from an uploaded prescription
    public void addMedication(Medication medication) {
        medications.add(medication);
    }
    
    // Add a medicine with dosage information
    public void addMedicineWithDosage(Medicine medicine, String dosage, int duration) {
        if (!medicines.contains(medicine)) {
            medicines.add(medicine);
        }
        
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, duration);
        medicinesDosage.add(medicineDosage);
    }
    
    // Add a medicine with dosage and special instructions
    public void addMedicineWithDosage(Medicine medicine, String dosage, int duration, String specialInstructions) {
        if (!medicines.contains(medicine)) {
            medicines.add(medicine);
        }
        
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, duration, specialInstructions);
        medicinesDosage.add(medicineDosage);
    }
    
    // Vararg method to add instructions
    public void addInstructions(String... newInstructions) {
        for (String instruction : newInstructions) {
            this.instructions.add(instruction);
        }
    }
    
    // Verify the prescription
    public void verify(String comments) {
        this.isVerified = true;
        this.verificationComments = comments;
    }
    
    // Check if prescription is expired
    public boolean isExpired() {
        Date currentDate = new Date();
        return currentDate.after(expiryDate);
    }
    
    // Method to check if a medicine is in this prescription
    public boolean containsMedicine(String medicineId) {
        for (Medicine medicine : medicines) {
            if (medicine.getMedicineId().equals(medicineId)) {
                return true;
            }
        }
        return false;
    }
    
    // Getters and Setters
    public String getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public List<Medicine> getMedicines() { return medicines; }
    public void setMedicines(List<Medicine> medicines) { this.medicines = medicines; }
    public List<String> getInstructions() { return instructions; }
    public void setInstructions(List<String> instructions) { this.instructions = instructions; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public String getPrescriptionImagePath() { return prescriptionImagePath; }
    public void setPrescriptionImagePath(String prescriptionImagePath) { this.prescriptionImagePath = prescriptionImagePath; }
    public String getVerificationComments() { return verificationComments; }
    public void setVerificationComments(String verificationComments) { this.verificationComments = verificationComments; }
    public List<MedicineDosage> getMedicinesDosage() { return medicinesDosage; }
    public List<Medication> getMedications() { return medications; }
    public void setMedications(List<Medication> medications) { this.medications = medications; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prescription ID: ").append(prescriptionId).append("\n");
        sb.append("Patient: ").append(patientName).append("\n");
        sb.append("Doctor: ").append(doctorName).append("\n");
        sb.append("Diagnosis: ").append(diagnosis).append("\n");
        sb.append("Issue Date: ").append(issueDate).append("\n");
        sb.append("Expiry Date: ").append(expiryDate).append("\n");
        sb.append("Medicines: \n");
        
        for (MedicineDosage md : medicinesDosage) {
            sb.append("- ").append(md.toString()).append("\n");
        }
        
        sb.append("Instructions: \n");
        for (String instruction : instructions) {
            sb.append("- ").append(instruction).append("\n");
        }
        
        sb.append("Verified: ").append(isVerified ? "Yes" : "No");
        if (isVerified && !verificationComments.isEmpty()) {
            sb.append(" (").append(verificationComments).append(")");
        }
        
        return sb.toString();
    }
} 