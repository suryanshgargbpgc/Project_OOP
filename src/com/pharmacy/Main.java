package com.pharmacy;

import com.pharmacy.model.*;
import com.pharmacy.service.*;
import com.pharmacy.service.impl.*;
import com.pharmacy.exception.*;

import java.util.*;

/**
 * Main class for Online Pharmacy System
 * Created by: Student 123456
 * Date: 10/30/2023
 */
public class Main {
    
    public static void main(String[] args) {
        // Print welcome message
        System.out.println("*******************************************");
        System.out.println("*   WELCOME TO MY PHARMACY SYSTEM        *");
        System.out.println("*******************************************");
        
        try {
            // Create services
            PrescriptionService prescriptionSvc = new PrescriptionServiceImpl();
            MedicineRecommendationSystem recommendSvc = new MedicineRecommendationSystemImpl();
            
            // Make test data
            Customer testCustomer = makeTestCustomer();
            Doctor testDoctor = makeTestDoctor();
            Medicine[] medicineCatalog = makeTestMedicines();
            
            // Create scanner for user input
            Scanner scanner = new Scanner(System.in);
            boolean shouldExit = false;
            
            // Main program loop
            while (shouldExit == false) {
                // Show menu options
                System.out.println("\nWhat do you want to do?");
                System.out.println("1. Show customer and doctor info");
                System.out.println("2. Show available medicines");
                System.out.println("3. Create a prescription");
                System.out.println("4. Place an order");
                System.out.println("5. Get medicine recommendations");
                System.out.println("6. Exit program");
                
                System.out.print("\nType your choice (1-6): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear input buffer
                
                // Process user choice
                if (choice == 1) {
                    showUserInfo(testCustomer, testDoctor);
                }
                else if (choice == 2) {
                    showMedicineList(medicineCatalog);
                }
                else if (choice == 3) {
                    makePrescription(testDoctor, testCustomer, medicineCatalog);
                }
                else if (choice == 4) {
                    makeOrder(testCustomer, medicineCatalog);
                }
                else if (choice == 5) {
                    findMedicineRecommendations(recommendSvc);
                }
                else if (choice == 6) {
                    shouldExit = true;
                    System.out.println("Thanks for using My Pharmacy System! Goodbye!");
                }
                else {
                    System.out.println("Sorry, that's not a valid choice. Try again.");
                }
            }
            
            // Close scanner
            scanner.close();
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Create a test customer
    public static Customer makeTestCustomer() {
        Customer c = new Customer(
            "C12345", 
            "John Smith", 
            "john@email.com", 
            "555-123-4567"
        );
        c.setAddress("123 Main St, Anytown, USA");
        return c;
    }
    
    // Create a test doctor
    public static Doctor makeTestDoctor() {
        Doctor d = new Doctor(
            "D98765", 
            "Dr. Sarah Jones", 
            "dr.jones@email.com", 
            "555-987-6543", 
            "MD12345", 
            "Family Medicine"
        );
        d.setConsultationFee(80.0);
        return d;
    }
    
    // Create test medicines
    public static Medicine[] makeTestMedicines() {
        // Create array to hold medicines
        Medicine[] meds = new Medicine[4];
        
        // Create medicine 1
        Medicine med1 = new Medicine("M001", "Aspirin", 4.99, false);
        med1.setDescription("Common pain reliever");
        med1.setCategory("Pain Relief");
        med1.setStock(120);
        meds[0] = med1;
        
        // Create medicine 2
        Medicine med2 = new Medicine("M002", "Claritin", 8.50, false);
        med2.setDescription("For allergies and hay fever");
        med2.setCategory("Allergy");
        med2.setStock(85);
        meds[1] = med2;
        
        // Create medicine 3
        Medicine med3 = new Medicine("M003", "Amoxicillin", 12.75, true);
        med3.setDescription("Antibiotic for bacterial infections");
        med3.setCategory("Antibiotic");
        med3.setStock(45);
        meds[2] = med3;
        
        // Create medicine 4
        Medicine med4 = new Medicine("M004", "Insulin", 45.99, true);
        med4.setDescription("For managing diabetes");
        med4.setCategory("Diabetes");
        med4.setStock(30);
        meds[3] = med4;
        
        return meds;
    }
    
    // Display user information
    public static void showUserInfo(Customer customer, Doctor doctor) {
        // Print customer info
        System.out.println("\n------ CUSTOMER INFO ------");
        System.out.println("ID: " + customer.getUserId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("Address: " + customer.getAddress());
        
        // Print doctor info
        System.out.println("\n------ DOCTOR INFO ------");
        System.out.println("ID: " + doctor.getUserId());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Email: " + doctor.getEmail());
        System.out.println("Phone: " + doctor.getPhoneNumber());
        System.out.println("License: " + doctor.getLicenseNumber());
        System.out.println("Specialty: " + doctor.getSpecialization());
        System.out.println("Fee: $" + doctor.getConsultationFee());
    }
    
    // Display medicine list
    public static void showMedicineList(Medicine[] meds) {
        System.out.println("\n------ AVAILABLE MEDICINES ------");
        System.out.println("ID       Name                 Price($)    Category      Stock  Prescription");
        System.out.println("---------------------------------------------------------------------------------");
        
        // Loop through all medicines and print them
        for (int i = 0; i < meds.length; i++) {
            Medicine m = meds[i];
            System.out.printf("%-8s %-20s %-12.2f %-14s %-6d %s\n", 
                           m.getMedicineId(), 
                           m.getName(), 
                           m.getPrice(), 
                           m.getCategory(), 
                           m.getStock(), 
                           m.isRequiresPrescription() ? "Yes" : "No");
        }
    }
    
    // Create a prescription
    public static void makePrescription(Doctor doctor, Customer customer, Medicine[] meds) {
        System.out.println("\n------ CREATE PRESCRIPTION ------");
        
        // Get user input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter diagnosis: ");
        String diagnosis = input.nextLine();
        
        // For this demo, just use specific medicines
        Medicine[] prescribedMeds = new Medicine[2];
        prescribedMeds[0] = meds[2]; // Amoxicillin
        prescribedMeds[1] = meds[0]; // Aspirin
        
        // Create prescription
        Prescription prescription = doctor.issuePrescription(customer, diagnosis, prescribedMeds);
        
        // Add some instructions
        prescription.addInstructions(
            "Take medicine as directed on bottle",
            "Drink plenty of water",
            "Get lots of rest"
        );
        
        // Mark as verified
        prescription.verify("Verified by " + doctor.getName());
        
        // Show the prescription
        System.out.println("\n------ PRESCRIPTION DETAILS ------");
        System.out.println(prescription.toString());
        
        // Save to customer record
        customer.addPrescription(prescription);
        System.out.println("Prescription saved to customer record!");
    }
    
    // Create an order
    public static void makeOrder(Customer customer, Medicine[] meds) throws PaymentException {
        System.out.println("\n------ CREATE ORDER ------");
        
        // Make new order
        Order order = new Order(customer.getUserId(), customer.getAddress());
        
        // Add some items
        order.addItem(meds[0], 2); // 2 bottles of Aspirin
        order.addItem(meds[1], 1); // 1 box of Claritin
        
        // Show order summary
        System.out.println("\n------ ORDER SUMMARY ------");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomerId());
        System.out.println("Ship To: " + order.getShippingAddress());
        System.out.println("Date: " + order.getOrderDate());
        System.out.println("Status: " + order.getStatus());
        
        // Show items
        System.out.println("\nItems:");
        for (int i = 0; i < order.getItemCount(); i++) {
            Medicine item = order.getItemAtIndex(i);
            int qty = order.getQuantityAtIndex(i);
            double price = item.getPrice();
            double total = price * qty;
            
            System.out.printf("- %s (%s): %d x $%.2f = $%.2f\n", 
                item.getName(), 
                item.getMedicineId(), 
                qty, 
                price, 
                total);
        }
        
        // Show totals
        System.out.println("\nSubtotal: $" + String.format("%.2f", order.getSubtotal()));
        System.out.println("Shipping: $" + String.format("%.2f", order.getShippingCost()));
        System.out.println("Total: $" + String.format("%.2f", order.getTotalAmount()));
        
        // Process payment (in real app would collect card info)
        order.processPayment("CREDIT_CARD");
        System.out.println("\nPayment processed! Order status: " + order.getStatus());
    }
    
    // Find medicine recommendations
    public static void findMedicineRecommendations(MedicineRecommendationSystem recommender) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("\n------ MEDICINE RECOMMENDATION SYSTEM ------");
        System.out.println("What symptoms do you have? (separate with commas)");
        String symptomInput = input.nextLine();
        
        // Split the input into separate symptoms
        String[] symptoms = symptomInput.split(",");
        for (int i = 0; i < symptoms.length; i++) {
            symptoms[i] = symptoms[i].trim();
        }
        
        // Check if symptoms need doctor attention
        boolean needDoctor = recommender.isMedicalAttentionRequired(symptoms);
        if (needDoctor) {
            System.out.println("\n!!! WARNING: Your symptoms may require medical attention !!!");
            System.out.println("Please consult a doctor as soon as possible!");
            return;
        }
        
        // Get recommended medicines
        Medicine[] recommendedMeds = recommender.recommendMedicinesForSymptoms(symptoms);
        
        // Show recommendations
        System.out.println("\n------ RECOMMENDED MEDICINES ------");
        if (recommendedMeds.length == 0) {
            System.out.println("No specific medicines found for your symptoms.");
            System.out.println("Please consult with a healthcare professional.");
        } else {
            for (int i = 0; i < recommendedMeds.length; i++) {
                Medicine med = recommendedMeds[i];
                System.out.println("- " + med.getName() + ": " + med.getDescription());
                System.out.println("  Price: $" + String.format("%.2f", med.getPrice()));
                System.out.println("  Category: " + med.getCategory());
                System.out.println();
            }
        }
        
        // Show general advice
        String[] advice = recommender.getPrecautionsForSymptoms(symptoms);
        System.out.println("\n------ GENERAL ADVICE ------");
        for (int i = 0; i < advice.length; i++) {
            System.out.println("- " + advice[i]);
        }
    }
} 