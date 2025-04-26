package com.pharmacy.service.impl;

import com.pharmacy.model.Medicine;
import com.pharmacy.service.MedicineRecommendationSystem;

/**
 * This class helps find medicines for symptoms
 */
public class MedicineRecommendationSystemImpl implements MedicineRecommendationSystem {
    
    // Database of symptoms 
    private String[] allSymptoms;
    // Medicines for each symptom
    private Medicine[][] medicinesForEachSymptom;
    // Bad symptoms that need doctor
    private String[] badSymptoms;
    
    /**
     * Constructor - sets up data
     */
    public MedicineRecommendationSystemImpl() {
        // Load medicine data
        setupSymptomData();
        // Load danger symptoms
        setupDangerSymptoms();
    }
    
    /**
     * Find medicines that might help with symptoms
     */
    @Override
    public Medicine[] recommendMedicinesForSymptoms(String[] symptoms) {
        // Return empty array if no symptoms
        if (symptoms == null) {
            return new Medicine[0];
        }
        if (symptoms.length == 0) {
            return new Medicine[0];
        }
        
        // Count how many medicines we'll need
        int maxMedicines = 20;
        Medicine[] result = new Medicine[maxMedicines];
        int resultCount = 0;
        
        // Check each user symptom
        for (int i = 0; i < symptoms.length; i++) {
            String userSymptom = symptoms[i].toLowerCase().trim();
            
            // Look for matching symptom in our database
            for (int j = 0; j < allSymptoms.length; j++) {
                if (allSymptoms[j].equals(userSymptom)) {
                    // Found matching symptom! Add its medicines
                    Medicine[] medicines = medicinesForEachSymptom[j];
                    
                    // Add each medicine if not already in result
                    for (int k = 0; k < medicines.length; k++) {
                        if (!isMedicineAlreadyAdded(result, resultCount, medicines[k])) {
                            // Add to result if there's room
                            if (resultCount < maxMedicines) {
                                result[resultCount] = medicines[k];
                                resultCount++;
                            }
                        }
                    }
                    break;
                }
            }
        }
        
        // Create final array of exact size needed
        Medicine[] finalResult = new Medicine[resultCount];
        for (int i = 0; i < resultCount; i++) {
            finalResult[i] = result[i];
        }
        
        System.out.println("Found " + resultCount + " medicines");
        return finalResult;
    }
    
    /**
     * Check if medicine is already in our list
     */
    private boolean isMedicineAlreadyAdded(Medicine[] list, int count, Medicine med) {
        for (int i = 0; i < count; i++) {
            if (list[i].getMedicineId().equals(med.getMedicineId())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if symptoms need a doctor
     */
    @Override
    public boolean isMedicalAttentionRequired(String[] symptoms) {
        // If no symptoms, no need for doctor
        if (symptoms == null) {
            return false;
        }
        if (symptoms.length == 0) {
            return false;
        }
        
        // Check each symptom
        for (int i = 0; i < symptoms.length; i++) {
            String userSymptom = symptoms[i].toLowerCase().trim();
            
            // Check if it's in our danger list
            for (int j = 0; j < badSymptoms.length; j++) {
                if (badSymptoms[j].equals(userSymptom)) {
                    System.out.println("WARNING: " + userSymptom + " needs doctor attention!");
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Get safety advice for symptoms
     */
    @Override
    public String[] getPrecautionsForSymptoms(String[] symptoms) {
        // Simple advice for any symptom
        String[] advice = new String[5];
        advice[0] = "Get plenty of rest";
        advice[1] = "Drink lots of water";
        advice[2] = "Take medicine as directed";
        advice[3] = "Call doctor if you feel worse";
        advice[4] = "Wash hands often";
        
        return advice;
    }
    
    /**
     * Setup the symptom database
     */
    private void setupSymptomData() {
        // Create list of symptoms we know about
        allSymptoms = new String[7];
        allSymptoms[0] = "headache";
        allSymptoms[1] = "fever";
        allSymptoms[2] = "cough";
        allSymptoms[3] = "sore throat";
        allSymptoms[4] = "runny nose";
        allSymptoms[5] = "body ache";
        allSymptoms[6] = "nausea";
        
        // Create medicines for each symptom
        medicinesForEachSymptom = new Medicine[7][];
        
        // Create some sample medicines
        Medicine painkiller = new Medicine("M001", "Tylenol", 6.99, false);
        painkiller.setDescription("Helps with pain and fever");
        painkiller.setCategory("OTC");
        painkiller.setStock(100);
        
        Medicine antiInflam = new Medicine("M002", "Advil", 7.50, false);
        antiInflam.setDescription("Reduces inflammation and pain");
        antiInflam.setCategory("OTC");
        antiInflam.setStock(80);
        
        Medicine allergy = new Medicine("M003", "Benadryl", 9.25, false);
        allergy.setDescription("Helps with allergies and runny nose");
        allergy.setCategory("OTC");
        allergy.setStock(75);
        
        Medicine coldMed = new Medicine("M004", "NyQuil", 10.50, false);
        coldMed.setDescription("Helps with cough and cold");
        coldMed.setCategory("OTC");
        coldMed.setStock(60);
        
        // Assign medicines to symptoms
        medicinesForEachSymptom[0] = new Medicine[] { painkiller, antiInflam }; // headache
        medicinesForEachSymptom[1] = new Medicine[] { painkiller, antiInflam }; // fever
        medicinesForEachSymptom[2] = new Medicine[] { coldMed }; // cough
        medicinesForEachSymptom[3] = new Medicine[] { painkiller, coldMed }; // sore throat
        medicinesForEachSymptom[4] = new Medicine[] { allergy }; // runny nose
        medicinesForEachSymptom[5] = new Medicine[] { painkiller, antiInflam }; // body ache
        medicinesForEachSymptom[6] = new Medicine[] { painkiller }; // nausea
    }
    
    /**
     * Setup the list of dangerous symptoms
     */
    private void setupDangerSymptoms() {
        // Symptoms that need immediate medical care
        badSymptoms = new String[6];
        badSymptoms[0] = "chest pain";
        badSymptoms[1] = "difficulty breathing";
        badSymptoms[2] = "severe headache";
        badSymptoms[3] = "severe stomach pain";
        badSymptoms[4] = "passed out";
        badSymptoms[5] = "very high fever";
    }
} 