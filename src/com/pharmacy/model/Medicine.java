package com.pharmacy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Medicine class represents pharmaceutical products available in the pharmacy
 */
public class Medicine {
    private String medicineId;
    private String name;
    private double price;
    private int stock;
    private String description;
    private boolean requiresPrescription;
    private Date manufactureDate;
    private Date expiryDate;
    private List<String> sideEffects;
    private String category; // OTC, Prescription, Generic, etc.

    // Default constructor
    public Medicine() {
        this.medicineId = "";
        this.name = "";
        this.price = 0.0;
        this.stock = 0;
        this.description = "";
        this.requiresPrescription = false;
        this.manufactureDate = new Date();
        this.expiryDate = new Date();
        this.sideEffects = new ArrayList<>();
        this.category = "";
    }
    
    // Constructor with basic information
    public Medicine(String medicineId, String name, double price, boolean requiresPrescription) {
        this.medicineId = medicineId;
        this.name = name;
        this.price = price;
        this.stock = 0;
        this.description = "";
        this.requiresPrescription = requiresPrescription;
        this.manufactureDate = new Date();
        this.expiryDate = new Date();
        this.sideEffects = new ArrayList<>();
        this.category = "";
    }
    
    // Full constructor
    public Medicine(String medicineId, String name, double price,
                   int stock, String description, boolean requiresPrescription,
                   Date manufactureDate, Date expiryDate, String category) {
        this.medicineId = medicineId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.requiresPrescription = requiresPrescription;
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.sideEffects = new ArrayList<>();
        this.category = category;
    }
    
    // Method to check if medicine is in stock
    public boolean isInStock() {
        return stock > 0;
    }
    
    // Method to check if medicine is expired
    public boolean isExpired() {
        Date currentDate = new Date();
        return currentDate.after(expiryDate);
    }
    
    // Method to update stock when medicine is sold
    public boolean sell(int quantity) {
        if (stock >= quantity && !isExpired()) {
            stock -= quantity;
            return true;
        }
        return false;
    }
    
    // Method to restock medicine
    public void restock(int quantity) {
        this.stock += quantity;
    }
    
    // Vararg method to add side effects
    public void addSideEffects(String... effects) {
        for (String effect : effects) {
            this.sideEffects.add(effect);
        }
    }
    
    // Getters and Setters
    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequiresPrescription() {
        return requiresPrescription;
    }

    public void setRequiresPrescription(boolean requiresPrescription) {
        this.requiresPrescription = requiresPrescription;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<String> getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(List<String> sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    
    @Override
    public String toString() {
        return "Medicine{" +
                "medicineId='" + medicineId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", requiresPrescription=" + requiresPrescription +
                ", category='" + category + '\'' +
                '}';
    }
} 