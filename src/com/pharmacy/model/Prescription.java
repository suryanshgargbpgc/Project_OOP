package com.pharmacy.model;

import java.io.Serializable;
import java.util.*;

public class Prescription {

    public String prescID;
    public String custID;
    public String docID;
    public String patientName;
    public String doctorName;
    public Date issueDate;
    public Date expiryDate;
    public String diagnosis;
    public Medicine[] medicines;
    public int medicineCount;
    public String[] instructions;
    public int instructionCount;
    public boolean isVerified;
    public String presImgPath; // Path to uploaded prescription image
    public String verifyComments;
    
    // Simple medicine dosage information
    public class MedicineDosage {
        public Medicine medicine;
        public String dosage; // Example: "1 pill 2 times daily"
        public int days; // How many days to take
        public String notes;
        
        // Constructor with basic info
        public MedicineDosage(Medicine medicine, String dosage, int days) {
            this.medicine = medicine;
            this.dosage = dosage;
            this.days = days;
            this.notes = "";
        }
        
        // Constructor with all info
        public MedicineDosage(Medicine medicine, String dosage, int days, String notes) {
            this.medicine = medicine;
            this.dosage = dosage;
            this.days = days;
            this.notes = notes;
        }
        
        // Simple string representation
        public String toString() {
            String result = medicine.getMedName() + " - " + dosage + " for " + days + " days";
            if (!notes.equals("")) {
                result = result + ". Notes: " + notes;
            }
            return result;
        }
    }
    
    // Simple medication class for when prescriptions are uploaded as images
    public static class Medication implements Serializable {
        public String name;
        public String dosage;
        public String frequency;
        public String duration;
        public String instructions;
        
        // Empty constructor
        public Medication() {
            name = "";
            dosage = "";
            frequency = "";
            duration = "";
            instructions = "";
        }
        
        // Full constructor
        public Medication(String name, String dosage, String frequency, String duration, String instructions) {
            this.name = name;
            this.dosage = dosage;
            this.frequency = frequency;
            this.duration = duration;
            this.instructions = instructions;
        }
    }
    
    public MedicineDosage[] medicinesDosage;
    public int medicineDosageCount;
    public Medication[] medications;
    public int medicationCount;
    
    // Simple constructor
    public Prescription() {
        prescID = UUID.randomUUID().toString();
        custID = "";
        docID = "";
        patientName = "";
        doctorName = "";
        issueDate = new Date();
        expiryDate = new Date();
        diagnosis = "";
        medicines = new Medicine[10];
        medicineCount = 0;
        instructions = new String[10];
        instructionCount = 0;
        isVerified = false;
        presImgPath = "";
        verifyComments = "";
        medicinesDosage = new MedicineDosage[10];
        medicineDosageCount = 0;
        medications = new Medication[10];
        medicationCount = 0;
    }
    
    // Constructor with basic information
    public Prescription(String custID, String docID, String diagnosis) {
        prescID = UUID.randomUUID().toString();
        this.custID = custID;
        this.docID = docID;
        patientName = "";
        doctorName = "";
        issueDate = new Date();
        
        // Set expiry date to 30 days from now
        expiryDate = new Date();
        expiryDate.setTime(issueDate.getTime() + 30L * 24 * 60 * 60 * 1000);
        
        this.diagnosis = diagnosis;
        medicines = new Medicine[10];
        medicineCount = 0;
        instructions = new String[10];
        instructionCount = 0;
        isVerified = false;
        presImgPath = "";
        verifyComments = "";
        medicinesDosage = new MedicineDosage[10];
        medicineDosageCount = 0;
        medications = new Medication[10];
        medicationCount = 0;
    }
    
    // Getter and setter methods
    public String getPrescriptionId() {
        return prescID;
    }
    
    public void setPrescriptionId(String id) {
        this.prescID = id;
    }
    
    public String getCustomerId() {
        return custID;
    }
    
    public void setCustomerId(String id) {
        this.custID = id;
    }
    
    public String getDoctorId() {
        return docID;
    }
    
    public void setDoctorId(String id) {
        this.docID = id;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String name) {
        this.patientName = name;
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorName(String name) {
        this.doctorName = name;
    }
    
    public Date getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(Date date) {
        this.issueDate = date;
    }
    
    public Date getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(Date date) {
        this.expiryDate = date;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public boolean isVerified() {
        return isVerified;
    }
    
    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

    public String getImagePath() {
        return presImgPath;
    }

    public void setImagePath(String path) {
        this.presImgPath = path;
    }
    
    public String getVerificationComments() {
        return verifyComments;
    }
    
    public void setVerificationComments(String comments) {
        this.verifyComments = comments;
    }

    // Check if prescription is still valid
    public boolean isValid() {
        Date today = new Date();
        return isVerified && today.before(expiryDate);
    }
    
    // Add a medicine to the prescription
    public void addMedicine(Medicine medicine) {
        // Check if medicine is already in the prescription
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i] == medicine) {
                return; // Medicine already exists
            }
        }
        
        // Make array bigger if needed
        if (medicineCount >= medicines.length) {
            Medicine[] newArray = new Medicine[medicines.length * 2];
            for (int i = 0; i < medicineCount; i++) {
                newArray[i] = medicines[i];
            }
            medicines = newArray;
        }
        
        medicines[medicineCount] = medicine;
        medicineCount++;
    }
    
    // Add a medication from uploaded prescription
    public void addMedication(Medication medication) {
        // Make array bigger if needed
        if (medicationCount >= medications.length) {
            Medication[] newArray = new Medication[medications.length * 2];
            for (int i = 0; i < medicationCount; i++) {
                newArray[i] = medications[i];
            }
            medications = newArray;
        }
        
        medications[medicationCount] = medication;
        medicationCount++;
    }
    
    // Add medicine with dosage info
    public void addMedicineWithDosage(Medicine medicine, String dosage, int days) {
        // Add to medicines list first
        addMedicine(medicine);
        
        // Create dosage object
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, days);
        
        // Make array bigger if needed
        if (medicineDosageCount >= medicinesDosage.length) {
            MedicineDosage[] newArray = new MedicineDosage[medicinesDosage.length * 2];
            for (int i = 0; i < medicineDosageCount; i++) {
                newArray[i] = medicinesDosage[i];
            }
            medicinesDosage = newArray;
        }
        
        medicinesDosage[medicineDosageCount] = medicineDosage;
        medicineDosageCount++;
    }
    
    // Add medicine with dosage and special notes
    public void addMedicineWithDosage(Medicine medicine, String dosage, int days, String notes) {
        // Add to medicines list first
        addMedicine(medicine);
        
        // Create dosage object with notes
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, days, notes);
        
        // Make array bigger if needed
        if (medicineDosageCount >= medicinesDosage.length) {
            MedicineDosage[] newArray = new MedicineDosage[medicinesDosage.length * 2];
            for (int i = 0; i < medicineDosageCount; i++) {
                newArray[i] = medicinesDosage[i];
            }
            medicinesDosage = newArray;
        }
        
        medicinesDosage[medicineDosageCount] = medicineDosage;
        medicineDosageCount++;
    }
    
    // Add instructions to the prescription
    public void addInstructions(String... newInstructions) {
        for (String instruction : newInstructions) {
            // Make array bigger if needed
            if (instructionCount >= instructions.length) {
                String[] newArray = new String[instructions.length * 2];
                for (int i = 0; i < instructionCount; i++) {
                    newArray[i] = instructions[i];
                }
                instructions = newArray;
            }
            
            instructions[instructionCount] = instruction;
            instructionCount++;
        }
    }
    
    // Mark prescription as verified
    public void verify(String comments) {
        isVerified = true;
        verifyComments = comments;
    }
    
    // Check if prescription is expired
    public boolean isExpired() {
        Date today = new Date();
        return today.after(expiryDate);
    }
    
    // Check if prescription contains a specific medicine
    public boolean containsMedicine(String medicineId) {
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i].getID().equals(medicineId)) {
                return true;
            }
        }
        return false;
    }
    
    // Get a clean array of just the medicines (no empty slots)
    public Medicine[] getMedicinesArray() {
        Medicine[] result = new Medicine[medicineCount];
        for (int i = 0; i < medicineCount; i++) {
            result[i] = medicines[i];
        }
        return result;
    }
    
    // Get a clean array of just the instructions (no empty slots)
    public String[] getInstructionsArray() {
        String[] result = new String[instructionCount];
        for (int i = 0; i < instructionCount; i++) {
            result[i] = instructions[i];
        }
        return result;
    }
    
    // Get a clean array of just the medicine dosages (no empty slots)
    public MedicineDosage[] getMedicineDosagesArray() {
        MedicineDosage[] result = new MedicineDosage[medicineDosageCount];
        for (int i = 0; i < medicineDosageCount; i++) {
            result[i] = medicinesDosage[i];
        }
        return result;
    }
    
    // Get a clean array of just the medications (no empty slots)
    public Medication[] getMedicationsArray() {
        Medication[] result = new Medication[medicationCount];
        for (int i = 0; i < medicationCount; i++) {
            result[i] = medications[i];
        }
        return result;
    }
    
    // Simple string representation
    public String toString() {
        String output = "";
        output = output + "Prescription #" + prescID + "\n";
        output = output + "Patient: " + patientName + "\n";
        output = output + "Doctor: " + doctorName + "\n";
        output = output + "Diagnosis: " + diagnosis + "\n";
        output = output + "Date: " + issueDate + "\n";
        output = output + "Expires: " + expiryDate + "\n";
        output = output + "Verified: " + (isVerified ? "Yes" : "No") + "\n";
        
        output = output + "Medicines:\n";
        for (int i = 0; i < medicineCount; i++) {
            output = output + "- " + medicines[i].getMedName() + "\n";
        }
        
        output = output + "Instructions:\n";
        for (int i = 0; i < instructionCount; i++) {
            output = output + "- " + instructions[i] + "\n";
        }
        
        return output;
    }
} 