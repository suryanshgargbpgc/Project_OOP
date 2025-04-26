package com.pharmacy.model;

import java.util.Date;

/**
 * This class is for customers who buy medicines
 * Written by: Student
 * Date: 11/10/2023
 */
public class Customer extends User {
    public Prescription[] rxList;
    public int rxCount;
    
    // Empty constructor
    public Customer() {
        super();
        this.rxList = new Prescription[10]; // Start with 10 slots
        this.rxCount = 0;
    }
    
    // Basic constructor with info
    public Customer(String id, String name, String email, String phone) {
        super(id, name, email, phone);
        this.rxList = new Prescription[10]; // Start with 10 slots
        this.rxCount = 0;
    }
    
    // Tell what kind of user this is
    @Override
    public String getUserType() {
        return "Customer";
    }
    
    // Add a prescription to customer
    public void addRx(Prescription rx) {
        // Check if we need more space
        if (rxCount >= rxList.length) {
            // Make bigger array
            Prescription[] newList = new Prescription[rxList.length * 2];
            
            // Copy all old prescriptions
            for (int i = 0; i < rxList.length; i++) {
                newList[i] = rxList[i];
            }
            
            // Use the new bigger array
            rxList = newList;
        }
        
        // Add the new prescription
        this.rxList[rxCount] = rx;
        rxCount = rxCount + 1;
    }
    
    // Get all prescriptions
    public Prescription[] getRxList() {
        // Make new array with just the filled slots
        Prescription[] result = new Prescription[rxCount];
        
        // Copy only the real prescriptions
        for (int i = 0; i < rxCount; i++) {
            result[i] = rxList[i];
        }
        
        return result;
    }

    // Set all prescriptions at once
    public void setRxList(Prescription[] rxArray) {
        this.rxList = rxArray;
        this.rxCount = rxArray.length;
    }
    
    // Print customer information
    public String toString() {
        String output = "";
        output = output + "Customer ID: " + getUserId() + "\n";
        output = output + "Name: " + getName() + "\n";
        output = output + "Email: " + getEmail() + "\n";
        output = output + "Phone: " + getPhoneNumber() + "\n"; 
        output = output + "Address: " + getAddress() + "\n";
        output = output + "Number of prescriptions: " + rxCount;
        return output;
    }
} 