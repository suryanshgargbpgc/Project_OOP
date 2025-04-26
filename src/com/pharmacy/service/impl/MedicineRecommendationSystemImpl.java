package com.pharmacy.service.impl;

import com.pharmacy.model.Medicine;
import com.pharmacy.service.MedicineRecommendationSystem;

/**
 * This class helps find medicines for symptoms
 */
public class MedicineRecommendationSystemImpl implements MedicineRecommendationSystem {
    
    // Database of symptoms 
    private String[] AllSymptoms;
    // Medicines for each symptom
    private Medicine[][] medicinesForEachSymptom;
    // Bad symptoms that need doctor
    private String[] badSymptoms;
    
    /**
     * Constructor - sets up data
     */
    public MedicineRecommendationSystemImpl() {
        
        setupSymptomData();
        setupDangerSymptoms();
    }
    
    /**
     * Find medicines that might help with symptoms
     */
    @Override
    public Medicine[] recommendMedicinesForSymptoms(String[] symptoms) {
        if (symptoms == null) {
            return new Medicine[0];
        }
        if (symptoms.length == 0) {
            return new Medicine[0];
        }
    
        int maxMedicines = 20;
        Medicine[] result = new Medicine[maxMedicines];
        int resultCount = 0;
        
        // ye symptom check karega
        for (int i = 0; i < symptoms.length; i++) {
            String userSymptom = symptoms[i].toLowerCase().trim();
            
            // matching symptom check karega database se
            for (int j = 0; j < AllSymptoms.length; j++) {
                if (AllSymptoms[j].equals(userSymptom)) {
                    
                    Medicine[] medicines = medicinesForEachSymptom[j];
                    
                    // medicine add karega result mein agar already nahi hai to 
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
        
        // final array create karega Exact size ke liye 
        Medicine[] finalResult = new Medicine[resultCount];
        for (int i = 0; i < resultCount; i++) {
            finalResult[i] = result[i];
        }
        
        System.out.println("Found " + resultCount + " medicines");
        return finalResult;
    }
    
    /**
     * medicine check karega agar already hai to true return karega
     */
    private boolean isMedicineAlreadyAdded(Medicine[] list, int count, Medicine med) {
        for (int i = 0; i < count; i++) {
            if (list[i].getID().equals(med.getID())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * symptoms check karega agar doctor required hai to true return karega nhi to fir false 
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
            
            // check karega agar danger list mein hai to true return karega nhi to falsee 
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
        // Create list of symptoms jiska humko pata hai 
        AllSymptoms = new String[7];
        AllSymptoms[0] = "headache";
        AllSymptoms[1] = "fever";
        AllSymptoms[2] = "cough";
        AllSymptoms[3] = "sore throat";
        AllSymptoms[4] = "runny nose";
        AllSymptoms[5] = "body ache";
        AllSymptoms[6] = "nausea";
        
        // medicine create karega for each sytom 
        medicinesForEachSymptom = new Medicine[7][];
        
        // sample medicines 
        Medicine painkiller = new Medicine("M001", "Tylenol", 6.99, false);
        painkiller.setInfo("Helps with pain and fever");
        painkiller.setType("OTC");
        painkiller.setCount(100);
        
        Medicine antiInflam = new Medicine("M002", "Advil", 7.50, false);
        antiInflam.setInfo("Reduces inflammation and pain");
        antiInflam.setType("OTC");
        antiInflam.setCount(80);
        
        Medicine allergy = new Medicine("M003", "Benadryl", 9.25, false);
        allergy.setInfo("Helps with allergies and runny nose");
        allergy.setType("OTC");
        allergy.setCount(75);
        
        Medicine coldMed = new Medicine("M004", "NyQuil", 10.50, false);
        coldMed.setInfo("Helps with cough and cold");
        coldMed.setType("OTC");
        coldMed.setCount(60);
        
        // har medicine ko har ek  sypmotm se match
        medicinesForEachSymptom[0] = new Medicine[] { painkiller, antiInflam }; // headache
        medicinesForEachSymptom[1] = new Medicine[] { painkiller, antiInflam }; // fever
        medicinesForEachSymptom[2] = new Medicine[] { coldMed }; // cough
        medicinesForEachSymptom[3] = new Medicine[] { painkiller, coldMed }; // sore throat
        medicinesForEachSymptom[4] = new Medicine[] { allergy }; // runny nose
        medicinesForEachSymptom[5] = new Medicine[] { painkiller, antiInflam }; // body ache
        medicinesForEachSymptom[6] = new Medicine[] { painkiller }; // nausea
    }
    
    /**
     * dangerous sympotms ke liye list 
     */
    private void setupDangerSymptoms() {
        // symtoms which can be dangerous 
        badSymptoms = new String[6];
        badSymptoms[0] = "chest pain";
        badSymptoms[1] = "difficulty breathing";
        badSymptoms[2] = "severe headache";
        badSymptoms[3] = "severe stomach pain";
        badSymptoms[4] = "passed out";
        badSymptoms[5] = "very high fever";
    }
} 