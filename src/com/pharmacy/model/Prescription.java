package com.pharmacy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Prescription class represents a medical prescription issued by a doctor
 */
public class Prescription{

    private String prescriptionId;
    private String customerId;
    private String doctorId;
    private String patientName;
    private String doctorName;
    private Date issueDate;
    private Date expiryDate;
    private String diagnosis;
    private Medicine[] medicines;
    private int medicineCount;
    private String[] instructions;
    private int instructionCount;
    private boolean isVerified;
    private String prescriptionImagePath; // Path to uploaded prescription image
    private String verificationComments;
    
    // Inner class for medicine dosage instructions
    public class MedicineDosage {

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
    
    private MedicineDosage[] medicinesDosage;
    private int medicineDosageCount;
    private Medication[] medications;
    private int medicationCount;
    
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
        this.medicines = new Medicine[10]; // Initial capacity
        this.medicineCount = 0;
        this.instructions = new String[10]; // Initial capacity
        this.instructionCount = 0;
        this.isVerified = false;
        this.prescriptionImagePath = "";
        this.verificationComments = "";
        this.medicinesDosage = new MedicineDosage[10]; // Initial capacity
        this.medicineDosageCount = 0;
        this.medications = new Medication[10]; // Initial capacity
        this.medicationCount = 0;
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
        this.medicines = new Medicine[10]; // Initial capacity
        this.medicineCount = 0;
        this.instructions = new String[10]; // Initial capacity
        this.instructionCount = 0;
        this.isVerified = false;
        this.prescriptionImagePath = "";
        this.verificationComments = "";
        this.medicinesDosage = new MedicineDosage[10]; // Initial capacity
        this.medicineDosageCount = 0;
        this.medications = new Medication[10]; // Initial capacity
        this.medicationCount = 0;
    }
    
    // Check if the prescription is valid (not expired)
    public boolean isValid() {
        Date currentDate = new Date();
        return isVerified && currentDate.before(expiryDate);
    }
    
    // Add a medicine to the prescription
    public void addMedicine(Medicine medicine) {
        // Check if medicine already exists
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i] == medicine) {
                return;
            }
        }
        
        // Resize array if needed
        if (medicineCount >= medicines.length) {
            Medicine[] newMedicines = new Medicine[medicines.length * 2];
            System.arraycopy(medicines, 0, newMedicines, 0, medicines.length);
            medicines = newMedicines;
        }
        
        medicines[medicineCount++] = medicine;
    }
    
    // Add a medication from an uploaded prescription
    public void addMedication(Medication medication) {
        // Resize array if needed
        if (medicationCount >= medications.length) {
            Medication[] newMedications = new Medication[medications.length * 2];
            System.arraycopy(medications, 0, newMedications, 0, medications.length);
            medications = newMedications;
        }
        
        medications[medicationCount++] = medication;
    }
    
    // Add a medicine with dosage information
    public void addMedicineWithDosage(Medicine medicine, String dosage, int duration) {
        // Add to medicines array if not exists
        boolean exists = false;
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i] == medicine) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            // Resize array if needed
            if (medicineCount >= medicines.length) {
                Medicine[] newMedicines = new Medicine[medicines.length * 2];
                System.arraycopy(medicines, 0, newMedicines, 0, medicines.length);
                medicines = newMedicines;
            }
            
            medicines[medicineCount++] = medicine;
        }
        
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, duration);
        
        // Resize array if needed
        if (medicineDosageCount >= medicinesDosage.length) {
            MedicineDosage[] newMedicineDosages = new MedicineDosage[medicinesDosage.length * 2];
            System.arraycopy(medicinesDosage, 0, newMedicineDosages, 0, medicinesDosage.length);
            medicinesDosage = newMedicineDosages;
        }
        
        medicinesDosage[medicineDosageCount++] = medicineDosage;
    }
    
    // Add a medicine with dosage and special instructions
    public void addMedicineWithDosage(Medicine medicine, String dosage, int duration, String specialInstructions) {
        // Add to medicines array if not exists
        boolean exists = false;
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i] == medicine) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            // Resize array if needed
            if (medicineCount >= medicines.length) {
                Medicine[] newMedicines = new Medicine[medicines.length * 2];
                System.arraycopy(medicines, 0, newMedicines, 0, medicines.length);
                medicines = newMedicines;
            }
            
            medicines[medicineCount++] = medicine;
        }
        
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, duration, specialInstructions);
        
        // Resize array if needed
        if (medicineDosageCount >= medicinesDosage.length) {
            MedicineDosage[] newMedicineDosages = new MedicineDosage[medicinesDosage.length * 2];
            System.arraycopy(medicinesDosage, 0, newMedicineDosages, 0, medicinesDosage.length);
            medicinesDosage = newMedicineDosages;
        }
        
        medicinesDosage[medicineDosageCount++] = medicineDosage;
    }
    
    // Vararg method to add instructions
    public void addInstructions(String... newInstructions) {
        for (String instruction : newInstructions) {
            // Resize array if needed
            if (instructionCount >= instructions.length) {
                String[] newInstructionsArray = new String[instructions.length * 2];
                System.arraycopy(instructions, 0, newInstructionsArray, 0, instructions.length);
                instructions = newInstructionsArray;
            }
            
            instructions[instructionCount++] = instruction;
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
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i].getMedicineId().equals(medicineId)) {
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

    public Medicine[] getMedicines() { 
        // Return a trimmed array with only the filled elements
        Medicine[] result = new Medicine[medicineCount];
        System.arraycopy(medicines, 0, result, 0, medicineCount);
        return result; 
    }

    public void setMedicines(Medicine[] medicines) { 
        this.medicines = medicines; 
        this.medicineCount = medicines.length;
    }

    public String[] getInstructions() { 
        // Return a trimmed array with only the filled elements
        String[] result = new String[instructionCount];
        System.arraycopy(instructions, 0, result, 0, instructionCount);
        return result; 
    }

    public void setInstructions(String[] instructions) { 
        this.instructions = instructions; 
        this.instructionCount = instructions.length;
    }

    public boolean isVerified() { return isVerified; }

    public void setVerified(boolean verified) { isVerified = verified; }

    public String getPrescriptionImagePath() { return prescriptionImagePath; }

    public void setPrescriptionImagePath(String prescriptionImagePath) { this.prescriptionImagePath = prescriptionImagePath; }

    public String getVerificationComments() { return verificationComments; }

    public void setVerificationComments(String verificationComments) { this.verificationComments = verificationComments; }

    public MedicineDosage[] getMedicinesDosage() { 
        // Return a trimmed array with only the filled elements
        MedicineDosage[] result = new MedicineDosage[medicineDosageCount];
        System.arraycopy(medicinesDosage, 0, result, 0, medicineDosageCount);
        return result; 
    }

    public Medication[] getMedications() { 
        // Return a trimmed array with only the filled elements
        Medication[] result = new Medication[medicationCount];
        System.arraycopy(medications, 0, result, 0, medicationCount);
        return result; 
    }

    public void setMedications(Medication[] medications) { 
        this.medications = medications; 
        this.medicationCount = medications.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prescription ID: ").append(prescriptionId).append("\n");
        sb.append("Patient: ").append(patientName).append("\n");
        sb.append("Doctor: ").append(doctorName).append("\n");
        sb.append("Diagnosis: ").append(diagnosis).append("\n");
        sb.append("Issue Date: ").append(issueDate).append("\n");
        sb.append("Expires: ").append(expiryDate).append("\n");
        sb.append("Verified: ").append(isVerified ? "Yes" : "No").append("\n");
        
        sb.append("Medicines:\n");
        for (int i = 0; i < medicineCount; i++) {
            sb.append("- ").append(medicines[i].getName()).append("\n");
        }
        
        sb.append("Instructions:\n");
        for (int i = 0; i < instructionCount; i++) {
            sb.append("- ").append(instructions[i]).append("\n");
        }
        
        return sb.toString();
    }
} 