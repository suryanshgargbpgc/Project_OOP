package com.pharmacy;

import com.pharmacy.model.*;
import com.pharmacy.service.*;
import com.pharmacy.service.impl.*;
import com.pharmacy.util.*;
import com.pharmacy.exception.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * Main class to demonstrate the Online Pharmacy System
 */
public class Main {
    
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("Welcome to the Online Pharmacy and Medicine Delivery System");
        System.out.println("========================================================");
        
        try {
            // Initialize services
            PrescriptionService prescriptionService = new PrescriptionServiceImpl();
            MedicineRecommendationSystem recommendationSystem = new MedicineRecommendationSystemImpl();
            NotificationManager notificationManager = NotificationManager.getInstance();
            
            // Create demo data
            Customer customer = createDemoCustomer();
            Doctor doctor = createDemoDoctor();
            List<Medicine> medicines = createDemoMedicines();
            
            // Display menu
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            
            while (!exit) {
                System.out.println("\nPlease select an option:");
                System.out.println("1. Show doctor and customer information");
                System.out.println("2. Show available medicines");
                System.out.println("3. Create a prescription");
                System.out.println("4. Place an order");
                System.out.println("5. Get medicine recommendations for symptoms");
                System.out.println("6. Schedule medication reminder");
                System.out.println("7. Demonstrate multithreading for notifications");
                System.out.println("8. Demonstrate encryption for health data");
                System.out.println("9. Exit");
                
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        displayUserInformation(customer, doctor);
                        break;
                    case 2:
                        displayMedicines(medicines);
                        break;
                    case 3:
                        createAndDisplayPrescription(doctor, customer, medicines);
                        break;
                    case 4:
                        placeAndDisplayOrder(customer, medicines);
                        break;
                    case 5:
                        getMedicineRecommendations(recommendationSystem);
                        break;
                    case 6:
                        scheduleMedicationReminder(notificationManager, customer);
                        break;
                    case 7:
                        demonstrateMultithreading(notificationManager);
                        break;
                    case 8:
                        demonstrateEncryption();
                        break;
                    case 9:
                        exit = true;
                        System.out.println("Thank you for using the Online Pharmacy System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            
            // Cleanup
            scanner.close();
            notificationManager.shutdown(true);
            
        } catch (Exception e) {
            logger.severe("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Customer createDemoCustomer() {
        Customer customer = new Customer(
            "CUST001", 
            "John Smith", 
            "john.smith@example.com", 
            "+1-555-123-4567"
        );
        customer.setAddress("123 Main Street, Anytown, USA");
        customer.addHealthConditions("Hypertension", "Seasonal Allergies");
        customer.addAllergies("Penicillin", "Sulfa Drugs");
        return customer;
    }
    
    private static Doctor createDemoDoctor() {
        Doctor doctor = new Doctor(
            "DOC001", 
            "Dr. Jane Wilson", 
            "dr.jane@example.com", 
            "+1-555-987-6543", 
            "LICENSE123", 
            "General Medicine"
        );
        doctor.setQualification("MD, Internal Medicine");
        doctor.setYearsOfExperience(15);
        doctor.setAvailableForTeleconsultation(true);
        doctor.setConsultationFee(75.0);
        doctor.addTimeSlots("Monday 9:00 AM", "Monday 11:00 AM", "Tuesday 2:00 PM", "Thursday 10:00 AM");
        return doctor;
    }
    
    private static List<Medicine> createDemoMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        
        Medicine medicine1 = new Medicine("MED001", "Paracetamol", 5.99, false);
        medicine1.setManufacturer("PharmaCorp");
        medicine1.setDescription("Pain reliever and fever reducer");
        medicine1.setCategory("OTC");
        medicine1.setDosageForm("Tablet");
        medicine1.setStock(100);
        medicine1.addSideEffects("Nausea", "Rash (in rare cases)");
        medicines.add(medicine1);
        
        Medicine medicine2 = new Medicine("MED002", "Cetirizine", 8.99, false);
        medicine2.setManufacturer("AllergiCare");
        medicine2.setDescription("Antihistamine for allergy relief");
        medicine2.setCategory("OTC");
        medicine2.setDosageForm("Tablet");
        medicine2.setStock(75);
        medicine2.addSideEffects("Drowsiness", "Dry mouth");
        medicines.add(medicine2);
        
        Medicine medicine3 = new Medicine("MED003", "Amoxicillin", 12.99, true);
        medicine3.setManufacturer("BacteriaCure");
        medicine3.setDescription("Antibiotic for bacterial infections");
        medicine3.setCategory("Prescription");
        medicine3.setDosageForm("Capsule");
        medicine3.setStock(50);
        medicine3.addSideEffects("Diarrhea", "Nausea", "Rash");
        medicines.add(medicine3);
        
        Medicine medicine4 = new Medicine("MED004", "Insulin Glargine", 45.99, true);
        medicine4.setManufacturer("DiabeCare");
        medicine4.setDescription("Long-acting insulin for diabetes management");
        medicine4.setCategory("Prescription");
        medicine4.setDosageForm("Injectable Solution");
        medicine4.setStock(30);
        medicine4.addSideEffects("Hypoglycemia", "Injection site reactions");
        medicines.add(medicine4);
        
        return medicines;
    }
    
    private static void displayUserInformation(Customer customer, Doctor doctor) {
        System.out.println("\n===== CUSTOMER INFORMATION =====");
        System.out.println("ID: " + customer.getUserId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Health Conditions: " + customer.getHealthConditions());
        System.out.println("Allergies: " + customer.getAllergies());
        System.out.println("Loyalty Tier: " + customer.getLoyaltyProgram().getTier());
        
        System.out.println("\n===== DOCTOR INFORMATION =====");
        System.out.println("ID: " + doctor.getUserId());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Email: " + doctor.getEmail());
        System.out.println("Phone: " + doctor.getPhoneNumber());
        System.out.println("License: " + doctor.getLicenseNumber());
        System.out.println("Specialization: " + doctor.getSpecialization());
        System.out.println("Qualification: " + doctor.getQualification());
        System.out.println("Experience: " + doctor.getYearsOfExperience() + " years");
        System.out.println("Available for Teleconsultation: " + doctor.isAvailableForTeleconsultation());
        System.out.println("Consultation Fee: $" + doctor.getConsultationFee());
        System.out.println("Available Time Slots: " + doctor.getAvailableTimeSlots());
    }
    
    private static void displayMedicines(List<Medicine> medicines) {
        System.out.println("\n===== AVAILABLE MEDICINES =====");
        System.out.printf("%-8s %-20s %-15s %-12s %-15s %-6s %-15s\n", 
                         "ID", "Name", "Manufacturer", "Price ($)", "Category", "Stock", "Requires Rx");
        
        for (Medicine medicine : medicines) {
            System.out.printf("%-8s %-20s %-15s %-12.2f %-15s %-6d %-15s\n", 
                            medicine.getMedicineId(), 
                            medicine.getName(), 
                            medicine.getManufacturer(), 
                            medicine.getPrice(), 
                            medicine.getCategory(), 
                            medicine.getStock(), 
                            medicine.isRequiresPrescription() ? "Yes" : "No");
        }
    }
    
    private static void createAndDisplayPrescription(Doctor doctor, Customer customer, List<Medicine> medicines) {
        System.out.println("\n===== CREATING PRESCRIPTION =====");
        
        // Find non-OTC medicines
        List<Medicine> prescriptionMedicines = new ArrayList<>();
        for (Medicine medicine : medicines) {
            if (medicine.isRequiresPrescription()) {
                prescriptionMedicines.add(medicine);
            }
        }
        
        if (prescriptionMedicines.isEmpty()) {
            System.out.println("No prescription medicines available.");
            return;
        }
        
        // Display available prescription medicines
        System.out.println("Available prescription medicines:");
        for (int i = 0; i < prescriptionMedicines.size(); i++) {
            Medicine medicine = prescriptionMedicines.get(i);
            System.out.println((i + 1) + ". " + medicine.getName() + " - " + medicine.getDescription());
        }
        
        // Create the prescription using the first medicine (in a real system, doctor would choose)
        Medicine selectedMedicine = prescriptionMedicines.get(0);
        String diagnosis = "Bacterial Infection";
        
        Prescription prescription = doctor.issuePrescription(customer, diagnosis, selectedMedicine);
        
        // Display the prescription details
        System.out.println("\nPrescription created successfully.");
        System.out.println("Prescription ID: " + prescription.getPrescriptionId());
        System.out.println("Doctor: " + prescription.getDoctorName());
        System.out.println("Patient: " + prescription.getPatientName());
        System.out.println("Diagnosis: " + prescription.getDiagnosis());
        System.out.println("Issue Date: " + prescription.getIssueDate());
        System.out.println("Expiry Date: " + prescription.getExpiryDate());
        System.out.println("Medicines:");
        
        for (Medicine medicine : prescription.getMedicines()) {
            System.out.println("- " + medicine.getName() + " (" + medicine.getDosageForm() + ")");
        }
        
        // Add the prescription to the customer
        customer.addPrescription(prescription);
    }
    
    private static void placeAndDisplayOrder(Customer customer, List<Medicine> medicines) throws PaymentException {
        System.out.println("\n===== PLACING ORDER =====");
        
        // Create a new order
        Order order = new Order(customer.getUserId(), customer.getAddress());
        
        // Add medicines to the order (in a real system, customer would choose)
        Medicine medicine1 = medicines.get(0); // Paracetamol
        Medicine medicine2 = medicines.get(1); // Cetirizine
        
        order.addItem(medicine1, 2);
        order.addItem(medicine2, 1);
        
        // Display order details
        System.out.println("Order created with the following items:");
        for (Order.OrderItem item : order.getOrderItems()) {
            System.out.println("- " + item.getMedicine().getName() + " x " + item.getQuantity() + 
                             " @ $" + item.getPrice() + " = $" + item.getTotalPrice());
        }
        
        // Process payment
        order.processPayment(Order.PaymentMethod.CREDIT_CARD);
        order.updateStatus(Order.OrderStatus.CONFIRMED);
        
        // Generate tracking number
        order.generateTrackingNumber();
        
        // Display order summary
        System.out.println("\nOrder Summary:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer ID: " + order.getCustomerId());
        System.out.println("Total Amount: $" + order.getTotalAmount());
        System.out.println("Payment Method: " + order.getPaymentMethod());
        System.out.println("Shipping Address: " + order.getShippingAddress());
        System.out.println("Order Status: " + order.getStatus());
        System.out.println("Tracking Number: " + order.getTrackingNumber());
        
        // Add the order to the customer
        customer.addOrder(order);
    }
    
    private static void getMedicineRecommendations(MedicineRecommendationSystem recommendationSystem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== MEDICINE RECOMMENDATIONS BASED ON SYMPTOMS =====");
        
        System.out.println("Enter your symptoms (comma-separated):");
        String symptomsInput = scanner.nextLine();
        
        // Parse symptoms
        String[] symptomsArray = symptomsInput.split(",");
        List<String> symptoms = new ArrayList<>();
        for (String symptom : symptomsArray) {
            symptoms.add(symptom.trim());
        }
        
        // Check if medical attention is required
        if (recommendationSystem.isMedicalAttentionRequired(symptoms)) {
            System.out.println("\nWARNING: Your symptoms may require immediate medical attention. Please consult a healthcare professional.");
        }
        
        // Get recommended medicines
        List<Medicine> recommendedMedicines = recommendationSystem.recommendMedicinesForSymptoms(symptoms);
        
        if (recommendedMedicines.isEmpty()) {
            System.out.println("\nNo specific OTC medications found for your symptoms. Please consult a healthcare professional.");
        } else {
            System.out.println("\nRecommended over-the-counter medications for your symptoms:");
            for (Medicine medicine : recommendedMedicines) {
                System.out.println("- " + medicine.getName() + ": " + medicine.getDescription());
            }
        }
        
        // Get precautions
        List<String> precautions = recommendationSystem.getPrecautionsForSymptoms(symptoms);
        if (!precautions.isEmpty()) {
            System.out.println("\nRecommended precautions:");
            for (String precaution : precautions) {
                System.out.println("- " + precaution);
            }
        }
        
        // Get recommended specialists if applicable
        Map<String, Double> specialists = recommendationSystem.getRecommendedSpecialists(symptoms);
        if (!specialists.isEmpty()) {
            System.out.println("\nYou may want to consult the following specialists:");
            for (Map.Entry<String, Double> entry : specialists.entrySet()) {
                System.out.println("- " + entry.getKey());
            }
        }
    }
    
    private static void scheduleMedicationReminder(NotificationManager notificationManager, Customer customer) {
        System.out.println("\n===== SCHEDULE MEDICATION REMINDER =====");
        
        // Schedule reminders (in a real system, this would be based on user input)
        System.out.println("Scheduling medication reminders...");
        
        // Schedule email reminder
        notificationManager.scheduleReminder(
            customer.getEmail(),
            "Remember to take your Paracetamol medication.",
            1, // 1 second for demo purposes (would be hours in a real system)
            "email"
        );
        
        // Schedule SMS reminder
        notificationManager.scheduleReminder(
            customer.getPhoneNumber(),
            "Remember to take your Cetirizine medication.",
            2, // 2 seconds for demo purposes
            "sms"
        );
        
        System.out.println("Reminders scheduled successfully!");
        System.out.println("Email reminder will be sent to: " + customer.getEmail());
        System.out.println("SMS reminder will be sent to: " + customer.getPhoneNumber());
        
        // Wait a bit to allow some reminders to be sent
        try {
            System.out.println("Waiting for reminders to be processed...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private static void demonstrateMultithreading(NotificationManager notificationManager) throws InterruptedException {
        System.out.println("\n===== DEMONSTRATING MULTITHREADING =====");
        
        final int NUM_NOTIFICATIONS = 5;
        final CountDownLatch latch = new CountDownLatch(NUM_NOTIFICATIONS);
        
        System.out.println("Sending multiple notifications simultaneously using thread pool...");
        
        // Create a custom notification task that counts down the latch
        for (int i = 1; i <= NUM_NOTIFICATIONS; i++) {
            final int notificationNumber = i;
            notificationManager.notificationExecutor.submit(() -> {
                try {
                    System.out.println("Processing notification #" + notificationNumber + " on thread: " + 
                                     Thread.currentThread().getName());
                    // Simulate varying processing times
                    Thread.sleep(notificationNumber * 300);
                    System.out.println("Notification #" + notificationNumber + " processed successfully.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Wait for all notifications to complete
        System.out.println("Waiting for all notifications to complete...");
        latch.await();
        System.out.println("All notifications processed successfully using multiple threads!");
    }
    
    private static void demonstrateEncryption() {
        System.out.println("\n===== DEMONSTRATING ENCRYPTION FOR HEALTH DATA =====");
        
        try {
            // Generate encryption key and IV
            String key = SecurityUtil.generateAESKey();
            String iv = SecurityUtil.generateIV();
            
            System.out.println("Generated encryption key and initialization vector.");
            
            // Sample health data to encrypt
            String healthData = "Patient has hypertension and is taking Lisinopril 10mg daily.";
            System.out.println("\nOriginal health data: " + healthData);
            
            // Encrypt the data
            String encryptedData = SecurityUtil.encryptAES(healthData, key, iv);
            System.out.println("Encrypted health data: " + encryptedData);
            
            // Decrypt the data
            String decryptedData = SecurityUtil.decryptAES(encryptedData, key, iv);
            System.out.println("Decrypted health data: " + decryptedData);
            
            // Verify decryption worked correctly
            if (healthData.equals(decryptedData)) {
                System.out.println("\nEncryption and decryption successful! The data is protected during transmission and storage.");
            } else {
                System.out.println("\nError: Decrypted data does not match original data.");
            }
            
        } catch (Exception e) {
            System.out.println("Error demonstrating encryption: " + e.getMessage());
        }
    }
} 