package com.pharmacy.model;

import java.util.Date;

/**
 * This class is for doctors who can write prescriptions
 * Written by: Student
 * Date: 11/10/2023
 */
public class Doctor extends User {
    // Public variables to store doctor information
    public String docLicense;
    public String docType;
    public double docFee;
    
    // Empty constructor
    public Doctor() {
        super();
        this.docLicense = "";
        this.docType = "";
        this.docFee = 0.0;
    }
    
    // Basic constructor with information
    public Doctor(String id, String name, String email, String phone,
                 String license, String type) {
        super(id, name, email, phone);
        this.docLicense = license;
        this.docType = type;
        this.docFee = 0.0;
    }
    
    // Tell what kind of user this is
    @Override
    public String getUserType() {
        return "Doctor";
    }
    
    // Create a prescription for a patient
    public Prescription makePrescription(Customer patient, String problem, Medicine[] meds) {
        // Make a new prescription
        Prescription rx = new Prescription();
        
        // Set all the details
        rx.setDoctorId(this.getUserId());
        rx.setCustomerId(patient.getUserId());
        rx.setDoctorName(this.getName());
        rx.setPatientName(patient.getName());
        rx.setDiagnosis(problem);
        
        // Set today's date
        Date today = new Date();
        rx.setIssueDate(today);
        
        // Set expiry date to 30 days from now
        Date endDate = new Date();
        // Add 30 days in milliseconds
        long thirtyDaysMs = 30 * 24 * 60 * 60 * 1000;
        endDate.setTime(endDate.getTime() + thirtyDaysMs);
        rx.setExpiryDate(endDate);
        
        // Add all medicines to the prescription
        for (int i = 0; i < meds.length; i++) {
            Medicine med = meds[i];
            rx.addMedicine(med);
        }
        
        return rx;
    }
    
    // Get and set methods with simple names
    public String getLicense() {
        return docLicense;
    }

    public void setLicense(String license) {
        this.docLicense = license;
    }

    public String getType() {
        return docType;
    }

    public void setType(String type) {
        this.docType = type;
    }

    public double getFee() {
        return docFee;
    }

    public void setFee(double fee) {
        if (fee >= 0) {
            this.docFee = fee;
        } else {
            this.docFee = 0;
        }
    }
    
    // Print doctor information
    public String toString() {
        String output = "";
        output = output + "Doctor ID: " + getUserId() + "\n";
        output = output + "Name: " + getName() + "\n";
        output = output + "License: " + docLicense + "\n";
        output = output + "Specialization: " + docType + "\n";
        output = output + "Fee: $" + docFee;
        return output;
    }
} 