package com.pharmacy.model;

import java.util.Date;

public class Doctor extends User {
    public String docLicense;
    public String docType;
    public double docFee;
    
    public Doctor() {
        super();
        this.docLicense = "";
        this.docType = "";
        this.docFee = 0.0;
    }
    
    public Doctor(String id, String name, String email, String phone,
                 String license, String type) {
        super(id, name, email, phone);
        this.docLicense = license;
        this.docType = type;
        this.docFee = 0.0;
    }
    
    @Override
    public String getUserType() {
        return "Doctor";
    }
    
    public Prescription makePrescription(Customer patient, String problem, Medicine[] meds) {
        Prescription presc = new Prescription();
        
        presc.setDoctorId(this.getUserId());
        presc.setCustomerId(patient.getUserId());
        presc.setDoctorName(this.getName());
        presc.setPatientName(patient.getName());
        presc.setDiagnosis(problem);
        
        Date today = new Date();
        presc.setIssueDate(today);
        
        Date endDate = new Date();
        long thirtyDaysMs = 30 * 24 * 60 * 60 * 1000;
        endDate.setTime(endDate.getTime() + thirtyDaysMs);
        presc.setExpiryDate(endDate);
        
        for (int i = 0; i < meds.length; i++) {
            Medicine med = meds[i];
            presc.addMedicine(med);
        }
        
        return presc;
    }
    
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