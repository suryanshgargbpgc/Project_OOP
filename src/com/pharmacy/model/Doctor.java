package com.pharmacy.model;

import java.util.Date;

/**
 * Doctor class represents medical professionals who can issue prescriptions
 * and provide teleconsultation services
 */
public class Doctor extends User {
    private String licenseNumber;
    private String specialization;
    private boolean isAvailableForTeleconsultation;
    private String qualification;
    private int yearsOfExperience;
    private String[] availableTimeSlots;
    private int timeSlotCount;
    private double consultationFee;
    
    // Default constructor
    public Doctor() {
        super();
        this.licenseNumber = "";
        this.specialization = "";
        this.isAvailableForTeleconsultation = false;
        this.qualification = "";
        this.yearsOfExperience = 0;
        this.availableTimeSlots = new String[10]; // Initial capacity
        this.timeSlotCount = 0;
        this.consultationFee = 0.0;
    }
    
    // Constructor with basic information
    public Doctor(String userId, String name, String email, String phoneNumber,
                 String licenseNumber, String specialization) {
        super(userId, name, email, phoneNumber);
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.isAvailableForTeleconsultation = false;
        this.qualification = "";
        this.yearsOfExperience = 0;
        this.availableTimeSlots = new String[10]; // Initial capacity
        this.timeSlotCount = 0;
        this.consultationFee = 0.0;
    }
    
    // Full constructor
    public Doctor(String userId, String name, String email, String phoneNumber,
                 String address, Date dateOfBirth, String licenseNumber,
                 String specialization, boolean isAvailableForTeleconsultation,
                 String qualification, int yearsOfExperience, double consultationFee) {
        super(userId, name, email, phoneNumber, address, dateOfBirth);
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.isAvailableForTeleconsultation = isAvailableForTeleconsultation;
        this.qualification = qualification;
        this.yearsOfExperience = yearsOfExperience;
        this.availableTimeSlots = new String[10]; // Initial capacity
        this.timeSlotCount = 0;
        this.consultationFee = consultationFee;
    }
    
    // Implementation of abstract method from User
    @Override
    public String getUserType() {
        return "Doctor";
    }
    
    // Method to issue a prescription
    public Prescription issuePrescription(Customer customer, String diagnosis, Medicine... medicines) {
        Prescription prescription = new Prescription();
        prescription.setDoctorId(this.getUserId());
        prescription.setCustomerId(customer.getUserId());
        prescription.setDoctorName(this.getName());
        prescription.setPatientName(customer.getName());
        prescription.setDiagnosis(diagnosis);
        prescription.setIssueDate(new Date());
        
        // Add a default expiry date (30 days from now)
        Date expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime() + 30L * 24 * 60 * 60 * 1000);
        prescription.setExpiryDate(expiryDate);
        
        // Add medicines to prescription
        for (Medicine medicine : medicines) {
            prescription.addMedicine(medicine);
        }
        
        return prescription;
    }
    
    // Vararg method to add available time slots
    public void addTimeSlots(String... slots) {
        for (String slot : slots) {
            // Resize array if needed
            if (timeSlotCount >= availableTimeSlots.length) {
                String[] newTimeSlots = new String[availableTimeSlots.length * 2];
                System.arraycopy(availableTimeSlots, 0, newTimeSlots, 0, availableTimeSlots.length);
                availableTimeSlots = newTimeSlots;
            }
            
            this.availableTimeSlots[timeSlotCount++] = slot;
        }
    }
    
    // Getters and Setters
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean isAvailableForTeleconsultation() {
        return isAvailableForTeleconsultation;
    }

    public void setAvailableForTeleconsultation(boolean availableForTeleconsultation) {
        isAvailableForTeleconsultation = availableForTeleconsultation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String[] getAvailableTimeSlots() {
        // Return a trimmed array with only the filled elements
        String[] result = new String[timeSlotCount];
        System.arraycopy(availableTimeSlots, 0, result, 0, timeSlotCount);
        return result;
    }

    public void setAvailableTimeSlots(String[] availableTimeSlots) {
        this.availableTimeSlots = availableTimeSlots;
        this.timeSlotCount = availableTimeSlots.length;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", specialization='" + specialization + '\'' +
                ", isAvailableForTeleconsultation=" + isAvailableForTeleconsultation +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
} 