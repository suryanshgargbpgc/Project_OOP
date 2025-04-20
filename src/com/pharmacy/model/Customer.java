package com.pharmacy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Customer class represents users who can order medicines, upload prescriptions,
 * and have medical appointments
 */
public class Customer extends User {
    private List<Prescription> prescriptions;
    private List<Order> orders;
    private boolean isPremiumMember;
    private List<String> healthConditions;
    private List<String> allergies;
    
    // Static nested class for managing customer loyalty points
    public static class LoyaltyProgram {
        private int points;
        private String tier; // Bronze, Silver, Gold, Platinum
        
        public LoyaltyProgram() {
            this.points = 0;
            this.tier = "Bronze";
        }
        
        public void addPoints(int pointsToAdd) {
            this.points += pointsToAdd;
            updateTier();
        }
        
        public void usePoints(int pointsToUse) {
            if (pointsToUse <= points) {
                this.points -= pointsToUse;
                updateTier();
            }
        }
        
        private void updateTier() {
            if (points < 1000) {
                tier = "Bronze";
            } else if (points < 5000) {
                tier = "Silver";
            } else if (points < 10000) {
                tier = "Gold";
            } else {
                tier = "Platinum";
            }
        }
        
        public int getDiscount() {
            switch (tier) {
                case "Bronze": return 0;
                case "Silver": return 5;
                case "Gold": return 10;
                case "Platinum": return 15;
                default: return 0;
            }
        }
        
        // Getters and setters
        public int getPoints() {
            return points;
        }
        
        public String getTier() {
            return tier;
        }
    }
    
    private LoyaltyProgram loyaltyProgram;
    
    // Default constructor
    public Customer() {
        super();
        this.prescriptions = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.isPremiumMember = false;
        this.healthConditions = new ArrayList<>();
        this.allergies = new ArrayList<>();
        this.loyaltyProgram = new LoyaltyProgram();
    }
    
    // Constructor with basic user information
    public Customer(String userId, String name, String email, String phoneNumber) {
        super(userId, name, email, phoneNumber);
        this.prescriptions = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.isPremiumMember = false;
        this.healthConditions = new ArrayList<>();
        this.allergies = new ArrayList<>();
        this.loyaltyProgram = new LoyaltyProgram();
    }
    
    // Full constructor
    public Customer(String userId, String name, String email, String phoneNumber, 
                   String address, Date dateOfBirth, boolean isPremiumMember) {
        super(userId, name, email, phoneNumber, address, dateOfBirth);
        this.prescriptions = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.isPremiumMember = isPremiumMember;
        this.healthConditions = new ArrayList<>();
        this.allergies = new ArrayList<>();
        this.loyaltyProgram = new LoyaltyProgram();
    }
    
    // Implementation of abstract method from User
    @Override
    public String getUserType() {
        return "Customer";
    }
    
    // Method to add a new prescription
    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }
    
    // Method to add a new order
    public void addOrder(Order order) {
        this.orders.add(order);
        // Add loyalty points based on order amount
        this.loyaltyProgram.addPoints((int) (order.getTotalAmount() * 10));
    }
    
    // Vararg method to add health conditions
    public void addHealthConditions(String... conditions) {
        for (String condition : conditions) {
            this.healthConditions.add(condition);
        }
    }
    
    // Vararg method to add allergies
    public void addAllergies(String... newAllergies) {
        for (String allergy : newAllergies) {
            this.allergies.add(allergy);
        }
    }
    
    // Getters and Setters
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isPremiumMember() {
        return isPremiumMember;
    }

    public void setPremiumMember(boolean premiumMember) {
        isPremiumMember = premiumMember;
    }

    public List<String> getHealthConditions() {
        return healthConditions;
    }

    public void setHealthConditions(List<String> healthConditions) {
        this.healthConditions = healthConditions;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
    
    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgram;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", prescriptions=" + prescriptions.size() +
                ", orders=" + orders.size() +
                ", isPremiumMember=" + isPremiumMember +
                ", loyaltyTier=" + loyaltyProgram.getTier() +
                '}';
    }
} 