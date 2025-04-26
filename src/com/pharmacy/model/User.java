package com.pharmacy.model;

import java.util.Date;

public abstract class User {
    public String userID;
    public String userName;
    public String userEmail;
    public String userPhone;
    public String userAddress;

    public User() {
        this.userID = "";
        this.userName = "";
        this.userEmail = "";
        this.userPhone = "";
        this.userAddress = "";
    }
    
    public User(String id, String name, String email, String phone) {
        this.userID = id;
        this.userName = name;
        this.userEmail = email;
        this.userPhone = phone;
        this.userAddress = "";
    }
    
    public User(String id, String name, String email, String phone, String address) {
        this.userID = id;
        this.userName = name;
        this.userEmail = email;
        this.userPhone = phone;
        this.userAddress = address;
    }
    
    public abstract String getUserType();
    
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