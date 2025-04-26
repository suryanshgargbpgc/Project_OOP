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
            Medicine[] medicines = createDemoMedicines();
            
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
    
    private static Medicine[] createDemoMedicines() {
        Medicine[] medicines = new Medicine[4];
        
        Medicine medicine1 = new Medicine("MED001", "Paracetamol", 5.99, false);
        medicine1.setDescription("Pain reliever and fever reducer");
        medicine1.setCategory("OTC");
        medicine1.setStock(100);
        medicine1.addSideEffects("Nausea", "Rash (in rare cases)");
        medicines[0] = medicine1;
        
        Medicine medicine2 = new Medicine("MED002", "Cetirizine", 8.99, false);
        medicine2.setDescription("Antihistamine for allergy relief");
        medicine2.setCategory("OTC");
        medicine2.setStock(75);
        medicine2.addSideEffects("Drowsiness", "Dry mouth");
        medicines[1] = medicine2;
        
        Medicine medicine3 = new Medicine("MED003", "Amoxicillin", 12.99, true);
        medicine3.setDescription("Antibiotic for bacterial infections");
        medicine3.setCategory("Prescription");
        medicine3.setStock(50);
        medicine3.addSideEffects("Diarrhea", "Nausea", "Rash");
        medicines[2] = medicine3;
        
        Medicine medicine4 = new Medicine("MED004", "Insulin Glargine", 45.99, true);
        medicine4.setDescription("Long-acting insulin for diabetes management");
        medicine4.setCategory("Prescription");
        medicine4.setStock(30);
        medicine4.addSideEffects("Hypoglycemia", "Injection site reactions");
        medicines[3] = medicine4;
        
        return medicines;
    }
    
    private static void displayUserInformation(Customer customer, Doctor doctor) {
        System.out.println("\n===== CUSTOMER INFORMATION =====");
        System.out.println("ID: " + customer.getUserId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Health Conditions: " + Arrays.toString(customer.getHealthConditions()));
        System.out.println("Allergies: " + Arrays.toString(customer.getAllergies()));
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
        System.out.println("Available Time Slots: " + Arrays.toString(doctor.getAvailableTimeSlots()));
    }
    
    private static void displayMedicines(Medicine[] medicines) {
        System.out.println("\n===== AVAILABLE MEDICINES =====");
        System.out.printf("%-8s %-20s %-15s %-12s %-15s %-6s %-15s\n", 
                         "ID", "Name", "Manufacturer", "Price ($)", "Category", "Stock", "Requires Rx");
        
        for (Medicine medicine : medicines) {
            System.out.printf("%-8s %-20s %-15s %-12.2f %-15s %-6d %-15s\n", 
                            medicine.getMedicineId(), 
                            medicine.getName(), 
                            medicine.getPrice(),
                            medicine.getPrice(), 
                            medicine.getCategory(), 
                            medicine.getStock(), 
                            medicine.isRequiresPrescription());
        }
    }
    
    private static void createAndDisplayPrescription(Doctor doctor, Customer customer, Medicine[] medicines) {
        System.out.println("\n===== CREATING PRESCRIPTION =====");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine();
        
        // Select medicines for prescription
        Medicine[] prescriptionMedicines = new Medicine[2]; // We'll use 2 medicines for demo
        prescriptionMedicines[0] = medicines[2]; // Amoxicillin
        prescriptionMedicines[1] = medicines[0]; // Paracetamol
        
        // Create prescription
        Prescription prescription = doctor.issuePrescription(customer, diagnosis, prescriptionMedicines);
        
        // Add instructions
        prescription.addInstructions(
            "Take medications as prescribed",
            "Drink plenty of fluids",
            "Rest well and avoid strenuous activities",
            "Contact doctor if symptoms worsen"
        );
        
        // Verify the prescription
        prescription.verify("Prescription verified by Dr. " + doctor.getName());
        
        // Display prescription details
        System.out.println("\n===== PRESCRIPTION DETAILS =====");
        System.out.println(prescription.toString());
        
        // Add prescription to customer records
        customer.addPrescription(prescription);
        System.out.println("Prescription added to customer records successfully.");
    }
    
    private static void placeAndDisplayOrder(Customer customer, Medicine[] medicines) throws PaymentException {
        System.out.println("\n===== PLACING ORDER =====");
        
        // Create a new order
        Order order = new Order(customer.getUserId(), customer.getAddress());
        
        // Add medicines to order
        order.addItem(medicines[0], 2); // 2 units of Paracetamol
        order.addItem(medicines[1], 1); // 1 unit of Cetirizine
        
        // Display order summary before payment
        System.out.println("\n===== ORDER SUMMARY =====");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + customer.getName());
        System.out.println("Items:");
        for (Order.OrderItem item : order.getOrderItems()) {
            System.out.println("- " + item.toString());
        }
        System.out.println("Total Amount: $" + order.getTotalAmount());
        
        // Process payment
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelect payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Cash on Delivery");
        
        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();
        
        Order.PaymentMethod paymentMethod;
        switch (choice) {
            case 1:
                paymentMethod = Order.PaymentMethod.CREDIT_CARD;
                break;
            case 2:
                paymentMethod = Order.PaymentMethod.DEBIT_CARD;
                break;
            case 3:
                paymentMethod = Order.PaymentMethod.CASH_ON_DELIVERY;
                break;
            default:
                paymentMethod = Order.PaymentMethod.CASH_ON_DELIVERY;
        }
        
        boolean paymentSuccessful = order.processPayment(paymentMethod);
        
        if (paymentSuccessful) {
            System.out.println("\nPayment successful!");
            order.updateStatus(Order.OrderStatus.CONFIRMED);
            order.generateTrackingNumber();
            
            // Add order to customer records
            customer.addOrder(order);
            
            // Display final order details
            System.out.println("\n===== ORDER CONFIRMED =====");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Tracking Number: " + order.getTrackingNumber());
            System.out.println("Customer earned " + ((int)(order.getTotalAmount() * 10)) + " loyalty points!");
        } else {
            throw new PaymentException("Payment failed. Please try again.");
        }
    }
    
    private static void getMedicineRecommendations(MedicineRecommendationSystem recommendationSystem) {
        System.out.println("\n===== MEDICINE RECOMMENDATIONS =====");
        
        // Get symptoms from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter symptoms (comma separated):");
        String symptomsInput = scanner.nextLine();
        
        String[] symptoms = symptomsInput.split(",");
        for (int i = 0; i < symptoms.length; i++) {
            symptoms[i] = symptoms[i].trim();
        }
        
        // Check if medical attention is required
        boolean needsMedicalAttention = recommendationSystem.isMedicalAttentionRequired(symptoms);
        
        if (needsMedicalAttention) {
            System.out.println("\nWARNING: Based on your symptoms, immediate medical attention may be required.");
            System.out.println("Please consult a healthcare professional or visit an emergency room.");
            
            Map<String, Double> specialists = recommendationSystem.getRecommendedSpecialists(symptoms);
            
            System.out.println("\nRecommended Specialists:");
            for (Map.Entry<String, Double> entry : specialists.entrySet()) {
                System.out.printf("- %s (Confidence: %.1f%%)\n", entry.getKey(), entry.getValue() * 100);
            }
        } else {
            // Get medicine recommendations
            Medicine[] recommendedMedicines = recommendationSystem.recommendMedicinesForSymptoms(symptoms);
            
            if (recommendedMedicines.length > 0) {
                System.out.println("\nRecommended Medicines:");
                for (Medicine medicine : recommendedMedicines) {
                    System.out.println("- " + medicine.getName() + " (" + medicine.getDescription() + ")");
                    System.out.println("  Price: $" + medicine.getPrice());
                    System.out.println("  Requires Prescription: " + (medicine.isRequiresPrescription() ? "Yes" : "No"));
                    System.out.println("  Possible Side Effects: " + Arrays.toString(medicine.getSideEffects()));
                    System.out.println();
                }
            } else {
                System.out.println("No specific medications recommended for the symptoms provided.");
            }
            
            // Get precautions
            String[] precautions = recommendationSystem.getPrecautionsForSymptoms(symptoms);
            
            if (precautions.length > 0) {
                System.out.println("\nRecommended Precautions:");
                for (String precaution : precautions) {
                    System.out.println("- " + precaution);
                }
            }
        }
    }
    
    private static void scheduleMedicationReminder(NotificationManager notificationManager, Customer customer) {
        System.out.println("\n===== MEDICATION REMINDER SETUP =====");
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Let's set up a medication reminder");
        System.out.print("Enter medication name: ");
        String medicationName = scanner.nextLine();
        
        System.out.print("Enter dosage instructions: ");
        String dosage = scanner.nextLine();
        
        System.out.print("When do you need to take this medication? (e.g., 'Morning', 'Evening'): ");
        String time = scanner.nextLine();
        
        System.out.print("How many hours from now for the first reminder? ");
        int hours = scanner.nextInt();
        
        // Create the reminder
        String reminderMessage = String.format("Time to take %s (%s). %s", 
                                medicationName, dosage, time);
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, hours);
        Date reminderTime = calendar.getTime();
        
        // Schedule the reminder
        notificationManager.scheduleNotification(
            customer.getUserId(),
            reminderMessage,
            reminderTime,
            NotificationType.MEDICATION_REMINDER
        );
        
        System.out.println("\nReminder scheduled successfully for " + reminderTime);
        System.out.println("You will receive a notification: \"" + reminderMessage + "\"");
    }
    
    private static void demonstrateMultithreading(NotificationManager notificationManager) throws InterruptedException {
        System.out.println("\n===== MULTITHREADING DEMONSTRATION =====");
        System.out.println("Simulating multiple concurrent operations in the pharmacy system...");
        
        // CountDownLatch to wait for all threads to complete
        final int THREAD_COUNT = 5;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        // Create and start threads for different operations
        Thread orderProcessingThread = new Thread(() -> {
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Processing orders...");
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Orders processed.");
            latch.countDown();
        });
        
        Thread inventoryUpdateThread = new Thread(() -> {
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Updating inventory...");
            try { Thread.sleep(1500); } catch (InterruptedException e) { }
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Inventory updated.");
            latch.countDown();
        });
        
        Thread notificationThread = new Thread(() -> {
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Sending notifications...");
            try { Thread.sleep(800); } catch (InterruptedException e) { }
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Notifications sent.");
            latch.countDown();
        });
        
        Thread prescriptionVerificationThread = new Thread(() -> {
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Verifying prescriptions...");
            try { Thread.sleep(1200); } catch (InterruptedException e) { }
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Prescriptions verified.");
            latch.countDown();
        });
        
        Thread reportGenerationThread = new Thread(() -> {
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Generating reports...");
            try { Thread.sleep(2000); } catch (InterruptedException e) { }
            System.out.println("[Thread " + Thread.currentThread().getId() + "] Reports generated.");
            latch.countDown();
        });
        
        // Start all threads
        orderProcessingThread.start();
        inventoryUpdateThread.start();
        notificationThread.start();
        prescriptionVerificationThread.start();
        reportGenerationThread.start();
        
        // Wait for all threads to complete
        System.out.println("Waiting for all operations to complete...");
        latch.await();
        System.out.println("All concurrent operations completed successfully!");
    }
    
    private static void demonstrateEncryption() {
        System.out.println("\n===== ENCRYPTION DEMONSTRATION =====");
        
        // Sample sensitive data
        String patientData = "Name: John Smith, DOB: 1985-03-12, Condition: Hypertension, Medication: Lisinopril 10mg";
        
        System.out.println("Original sensitive data:");
        System.out.println(patientData);
        
        try {
            // Generate encryption key
            System.out.println("\nGenerating encryption key...");
            String encryptionKey = EncryptionUtil.generateKey();
            
            // Encrypt data
            System.out.println("Encrypting patient data...");
            String encryptedData = EncryptionUtil.encrypt(patientData, encryptionKey);
            
            System.out.println("\nEncrypted data (would be stored in database or transmitted):");
            System.out.println(encryptedData);
            
            // Decrypt data
            System.out.println("\nDecrypting patient data...");
            String decryptedData = EncryptionUtil.decrypt(encryptedData, encryptionKey);
            
            System.out.println("\nDecrypted data:");
            System.out.println(decryptedData);
            
            // Verify data integrity
            if (patientData.equals(decryptedData)) {
                System.out.println("\nEncryption and decryption successful - data integrity maintained!");
            } else {
                System.out.println("\nError: Decrypted data does not match original data!");
            }
            
        } catch (Exception e) {
            System.out.println("Error during encryption process: " + e.getMessage());
        }
    }
} 