package com.pharmacy.model;

import java.util.Date;

/**
 * Customer class represents users who can order medicines, upload prescriptions,
 * and have medical appointments
 */
public class Customer extends User {
    private Prescription[] prescriptions;
    private int prescriptionCount;
    private Order[] orders;
    private int orderCount;
    private boolean isPremiumMember;
    private String[] healthConditions;
    private int healthConditionCount;
    private String[] allergies;
    private int allergyCount;
    
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
        this.prescriptions = new Prescription[10]; // Initial capacity
        this.prescriptionCount = 0;
        this.orders = new Order[10]; // Initial capacity
        this.orderCount = 0;
        this.isPremiumMember = false;
        this.healthConditions = new String[10]; // Initial capacity
        this.healthConditionCount = 0;
        this.allergies = new String[10]; // Initial capacity
        this.allergyCount = 0;
        this.loyaltyProgram = new LoyaltyProgram();
    }
    
    // Constructor with basic user information
    public Customer(String userId, String name, String email, String phoneNumber) {
        super(userId, name, email, phoneNumber);
        this.prescriptions = new Prescription[10]; // Initial capacity
        this.prescriptionCount = 0;
        this.orders = new Order[10]; // Initial capacity
        this.orderCount = 0;
        this.isPremiumMember = false;
        this.healthConditions = new String[10]; // Initial capacity
        this.healthConditionCount = 0;
        this.allergies = new String[10]; // Initial capacity
        this.allergyCount = 0;
        this.loyaltyProgram = new LoyaltyProgram();
    }
    
    // Full constructor
    public Customer(String userId, String name, String email, String phoneNumber, 
                   String address, Date dateOfBirth, boolean isPremiumMember) {
        super(userId, name, email, phoneNumber, address, dateOfBirth);
        this.prescriptions = new Prescription[10]; // Initial capacity
        this.prescriptionCount = 0;
        this.orders = new Order[10]; // Initial capacity
        this.orderCount = 0;
        this.isPremiumMember = isPremiumMember;
        this.healthConditions = new String[10]; // Initial capacity
        this.healthConditionCount = 0;
        this.allergies = new String[10]; // Initial capacity
        this.allergyCount = 0;
        this.loyaltyProgram = new LoyaltyProgram();
    }
    
    // Implementation of abstract method from User
    @Override
    public String getUserType() {
        return "Customer";
    }
    
    // Method to add a new prescription
    public void addPrescription(Prescription prescription) {
        // Resize array if needed
        if (prescriptionCount >= prescriptions.length) {
            Prescription[] newPrescriptions = new Prescription[prescriptions.length * 2];
            System.arraycopy(prescriptions, 0, newPrescriptions, 0, prescriptions.length);
            prescriptions = newPrescriptions;
        }
        
        this.prescriptions[prescriptionCount++] = prescription;
    }
    
    // Method to add a new order
    public void addOrder(Order order) {
        // Resize array if needed
        if (orderCount >= orders.length) {
            Order[] newOrders = new Order[orders.length * 2];
            System.arraycopy(orders, 0, newOrders, 0, orders.length);
            orders = newOrders;
        }
        
        this.orders[orderCount++] = order;
        // Add loyalty points based on order amount
        this.loyaltyProgram.addPoints((int) (order.getTotalAmount() * 10));
    }
    
    // Vararg method to add health conditions
    public void addHealthConditions(String... conditions) {
        for (String condition : conditions) {
            // Resize array if needed
            if (healthConditionCount >= healthConditions.length) {
                String[] newHealthConditions = new String[healthConditions.length * 2];
                System.arraycopy(healthConditions, 0, newHealthConditions, 0, healthConditions.length);
                healthConditions = newHealthConditions;
            }
            
            this.healthConditions[healthConditionCount++] = condition;
        }
    }
    
    // Vararg method to add allergies
    public void addAllergies(String... newAllergies) {
        for (String allergy : newAllergies) {
            // Resize array if needed
            if (allergyCount >= allergies.length) {
                String[] newAllergiesArray = new String[allergies.length * 2];
                System.arraycopy(allergies, 0, newAllergiesArray, 0, allergies.length);
                allergies = newAllergiesArray;
            }
            
            this.allergies[allergyCount++] = allergy;
        }
    }
    
    // Getters and Setters
    public Prescription[] getPrescriptions() {
        // Return a trimmed array with only the filled elements
        Prescription[] result = new Prescription[prescriptionCount];
        System.arraycopy(prescriptions, 0, result, 0, prescriptionCount);
        return result;
    }

    public void setPrescriptions(Prescription[] prescriptions) {
        this.prescriptions = prescriptions;
        this.prescriptionCount = prescriptions.length;
    }

    public Order[] getOrders() {
        // Return a trimmed array with only the filled elements
        Order[] result = new Order[orderCount];
        System.arraycopy(orders, 0, result, 0, orderCount);
        return result;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
        this.orderCount = orders.length;
    }

    public boolean isPremiumMember() {
        return isPremiumMember;
    }

    public void setPremiumMember(boolean premiumMember) {
        isPremiumMember = premiumMember;
    }

    public String[] getHealthConditions() {
        // Return a trimmed array with only the filled elements
        String[] result = new String[healthConditionCount];
        System.arraycopy(healthConditions, 0, result, 0, healthConditionCount);
        return result;
    }

    public void setHealthConditions(String[] healthConditions) {
        this.healthConditions = healthConditions;
        this.healthConditionCount = healthConditions.length;
    }

    public String[] getAllergies() {
        // Return a trimmed array with only the filled elements
        String[] result = new String[allergyCount];
        System.arraycopy(allergies, 0, result, 0, allergyCount);
        return result;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
        this.allergyCount = allergies.length;
    }
    
    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgram;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", prescriptions=" + prescriptionCount +
                ", orders=" + orderCount +
                ", isPremiumMember=" + isPremiumMember +
                ", loyaltyTier=" + loyaltyProgram.getTier() +
                '}';
    }
} 