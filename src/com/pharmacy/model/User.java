package com.pharmacy.model;

import java.util.Date;

/**
 * Base user class for the pharmacy system
 * Written by: Student
 * Date: 11/10/2023
 */
public abstract class User {
    // Public variables for easy access
    public String userID;
    public String userName;
    public String userEmail;
    public String userPhone;
    public String userAddress;
    public Date userBirth;
    
    // Empty constructor
    public User() {
        this.userID = "";
        this.userName = "";
        this.userEmail = "";
        this.userPhone = "";
        this.userAddress = "";
        this.userBirth = new Date();
    }
    
    // Basic constructor
    public User(String id, String name, String email, String phone) {
        this.userID = id;
        this.userName = name;
        this.userEmail = email;
        this.userPhone = phone;
        this.userAddress = "";
        this.userBirth = new Date();
    }
    
    // Full constructor with all info
    public User(String id, String name, String email, String phone, String address, Date birth) {
        this.userID = id;
        this.userName = name;
        this.userEmail = email;
        this.userPhone = phone;
        this.userAddress = address;
        this.userBirth = birth;
    }
    
    // Each user type must implement this
    public abstract String getUserType();
    
    // Basic get/set methods with simple names
    public String getUserId() {
        return userID;
    }

    public void setUserId(String id) {
        this.userID = id;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public String getPhoneNumber() {
        return userPhone;
    }

    public void setPhoneNumber(String phone) {
        this.userPhone = phone;
    }

    public String getAddress() {
        return userAddress;
    }

    public void setAddress(String address) {
        this.userAddress = address;
    }

    public Date getBirthDate() {
        return userBirth;
    }

    public void setBirthDate(Date birth) {
        this.userBirth = birth;
    }
    
    // Print user information
    public String toString() {
        String output = "";
        output = output + "User Information:\n";
        output = output + "ID: " + userID + "\n";
        output = output + "Name: " + userName + "\n";
        output = output + "Email: " + userEmail + "\n";
        output = output + "Phone: " + userPhone + "\n";
        output = output + "Address: " + userAddress;
        return output;
    }
} 