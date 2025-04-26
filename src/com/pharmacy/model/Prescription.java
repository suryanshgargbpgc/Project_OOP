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
    public String presImgPath;
    public String verifyComments;
    

    public class MedicineDosage {
        public Medicine medicine;
        public String dosage;
        public int days;

        public MedicineDosage(Medicine medicine, String dosage, int days) {
            this.medicine = medicine;
            this.dosage = dosage;
            this.days = days;
        }
        
        public MedicineDosage(Medicine medicine, String dosage, int days, String notes) {
            this.medicine = medicine;
            this.dosage = dosage;
            this.days = days;
        }
        
        public String toString() {
            String result = medicine.getMedName() + " - " + dosage + " for " + days + " days";
            return result;
        }
    }
    
    public static class Medication implements Serializable {
        public String name;
        public String dosage;
        public String frequency;
        public String duration;
        public String instructions;
        
        public Medication() {
            name = "";
            dosage = "";
            frequency = "";
            duration = "";
            instructions = "";
        }
        
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
    
    public Prescription(String custID, String docID, String diagnosis) {
        prescID = UUID.randomUUID().toString();
        this.custID = custID;
        this.docID = docID;
        patientName = "";
        doctorName = "";
        issueDate = new Date();
        
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

    public boolean isValid() {
        Date today = new Date();
        return isVerified && today.before(expiryDate);
    }
    
    public void addMedicine(Medicine medicine) {
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i] == medicine) {
                return;
            }
        }
        
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
    
    public void addMedication(Medication medication) {
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
    
    public void addMedicineWithDosage(Medicine medicine, String dosage, int days) {
        addMedicine(medicine);
        
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, days);

        increaseDosageArray(medicineDosage);
    }

    private void increaseDosageArray(MedicineDosage medicineDosage) {
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

    public void addMedicineWithDosage(Medicine medicine, String dosage, int days, String notes) {
        addMedicine(medicine);
        
        MedicineDosage medicineDosage = new MedicineDosage(medicine, dosage, days, notes);

        increaseDosageArray(medicineDosage);
    }

    public void addInstructions(String... newInstructions) {
        for (String instruction : newInstructions) {
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
    
    public void verify(String comments) {
        isVerified = true;
        verifyComments = comments;
    }
    
    public boolean isExpired() {
        Date today = new Date();
        return today.after(expiryDate);
    }
    
    public boolean containsMedicine(String medicineId) {
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i].getID().equals(medicineId)) {
                return true;
            }
        }
        return false;
    }
    
    public Medicine[] getMedicinesArray() {
        Medicine[] result = new Medicine[medicineCount];
        for (int i = 0; i < medicineCount; i++) {
            result[i] = medicines[i];
        }
        return result;
    }
    
    public String[] getInstructionsArray() {
        String[] result = new String[instructionCount];
        for (int i = 0; i < instructionCount; i++) {
            result[i] = instructions[i];
        }
        return result;
    }
    
    public MedicineDosage[] getMedicineDosagesArray() {
        MedicineDosage[] result = new MedicineDosage[medicineDosageCount];
        for (int i = 0; i < medicineDosageCount; i++) {
            result[i] = medicinesDosage[i];
        }
        return result;
    }
    
    public Medication[] getMedicationsArray() {
        Medication[] result = new Medication[medicationCount];
        for (int i = 0; i < medicationCount; i++) {
            result[i] = medications[i];
        }
        return result;
    }
    
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