package com.pharmacy.service.impl;

import com.pharmacy.model.Medicine;
import com.pharmacy.service.MedicineRecommendationSystem;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Implementation of MedicineRecommendationSystem interface
 * This class provides AI-powered recommendations for OTC medicines based on symptoms
 */
public class MedicineRecommendationSystemImpl implements MedicineRecommendationSystem {
    
    private static final Logger logger = Logger.getLogger(MedicineRecommendationSystemImpl.class.getName());
    
    // Database of symptoms and associated medicines (simulated)
    private Map<String, Medicine[]> symptomMedicineMap;
    
    // Database of symptom information (simulated)
    private Map<String, Map<String, Object>> symptomInfoMap;
    
    // Database of medicine interactions (simulated)
    private Map<String, Map<String, String>> medicineInteractionsMap;
    
    // Array of symptoms that require medical attention (simulated)
    private String[] criticalSymptoms;
    
    // Map of specialist recommendations by symptom (simulated)
    private Map<String, Map<String, Double>> symptomSpecialistMap;
    
    // Map of health advice by condition (simulated)
    private Map<String, String[]> healthAdviceMap;
    
    // Database of medicine side effects (simulated)
    private Map<String, String[]> medicineSideEffectsMap;
    
    /**
     * Constructor - initializes the simulated databases
     */
    public MedicineRecommendationSystemImpl() {
        initializeSymptomMedicineMap();
        initializeSymptomInfoMap();
        initializeMedicineInteractionsMap();
        initializeCriticalSymptoms();
        initializeSymptomSpecialistMap();
        initializeHealthAdviceMap();
        initializeMedicineSideEffectsMap();
    }
    
    @Override
    public Medicine[] recommendMedicinesForSymptoms(String[] symptoms) {
        if (symptoms == null || symptoms.length == 0) {
            return new Medicine[0];
        }
        
        // Map to count the occurrence of each medicine
        Map<String, Medicine> recommendedMedicines = new HashMap<>();
        Map<String, Integer> medicineOccurrences = new HashMap<>();
        
        // For each symptom, add its associated medicines to the recommendation map
        for (String symptom : symptoms) {
            String normalizedSymptom = normalizeSymptom(symptom);
            Medicine[] medicinesForSymptom = symptomMedicineMap.getOrDefault(normalizedSymptom, new Medicine[0]);
            
            for (Medicine medicine : medicinesForSymptom) {
                recommendedMedicines.put(medicine.getMedicineId(), medicine);
                medicineOccurrences.put(
                    medicine.getMedicineId(), 
                    medicineOccurrences.getOrDefault(medicine.getMedicineId(), 0) + 1
                );
            }
        }
        
        // Convert map values to array
        Medicine[] sortedMedicines = recommendedMedicines.values().toArray(new Medicine[0]);
        
        // Sort medicines by number of matching symptoms
        Arrays.sort(sortedMedicines, (m1, m2) -> 
            medicineOccurrences.get(m2.getMedicineId()) - medicineOccurrences.get(m1.getMedicineId())
        );
        
        logger.info("Recommended " + sortedMedicines.length + " medicines for symptoms: " + Arrays.toString(symptoms));
        return sortedMedicines;
    }
    
    @Override
    public Map<String, Object> getSymptomInformation(String symptom) {
        if (symptom == null || symptom.isEmpty()) {
            return new HashMap<>();
        }
        
        String normalizedSymptom = normalizeSymptom(symptom);
        Map<String, Object> info = symptomInfoMap.getOrDefault(normalizedSymptom, new HashMap<>());
        
        logger.info("Retrieved information for symptom: " + symptom);
        return info;
    }
    
    @Override
    public boolean isMedicalAttentionRequired(String[] symptoms) {
        if (symptoms == null || symptoms.length == 0) {
            return false;
        }
        
        for (String symptom : symptoms) {
            String normalizedSymptom = normalizeSymptom(symptom);
            for (String criticalSymptom : criticalSymptoms) {
                if (criticalSymptom.equals(normalizedSymptom)) {
                    logger.warning("Medical attention required for symptom: " + symptom);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public Map<String, Double> getRecommendedSpecialists(String[] symptoms) {
        if (symptoms == null || symptoms.length == 0) {
            return new HashMap<>();
        }
        
        Map<String, Double> specialistScores = new HashMap<>();
        
        for (String symptom : symptoms) {
            String normalizedSymptom = normalizeSymptom(symptom);
            Map<String, Double> specialistsForSymptom = symptomSpecialistMap.getOrDefault(normalizedSymptom, new HashMap<>());
            
            for (Map.Entry<String, Double> entry : specialistsForSymptom.entrySet()) {
                String specialist = entry.getKey();
                Double score = entry.getValue();
                specialistScores.put(specialist, specialistScores.getOrDefault(specialist, 0.0) + score);
            }
        }
        
        logger.info("Recommended specialists for symptoms: " + Arrays.toString(symptoms));
        return specialistScores;
    }
    
    @Override
    public String[] getPrecautionsForSymptoms(String[] symptoms) {
        if (symptoms == null || symptoms.length == 0) {
            return new String[0];
        }
        
        // Use a map to avoid duplicates
        Map<String, Boolean> precautionsMap = new HashMap<>();
        
        for (String symptom : symptoms) {
            String normalizedSymptom = normalizeSymptom(symptom);
            Map<String, Object> info = symptomInfoMap.getOrDefault(normalizedSymptom, new HashMap<>());
            
            if (info.containsKey("precautions")) {
                String[] symptomPrecautions = (String[]) info.get("precautions");
                for (String precaution : symptomPrecautions) {
                    precautionsMap.put(precaution, true);
                }
            }
        }
        
        // Convert map keys to array
        return precautionsMap.keySet().toArray(new String[0]);
    }
    
    @Override
    public Map<String, String[]> getPotentialSideEffects(String[] medicineIds) {
        if (medicineIds == null || medicineIds.length == 0) {
            return new HashMap<>();
        }
        
        Map<String, String[]> sideEffects = new HashMap<>();
        
        for (String medicineId : medicineIds) {
            String[] effects = medicineSideEffectsMap.getOrDefault(medicineId, new String[0]);
            sideEffects.put(medicineId, effects);
        }
        
        logger.info("Retrieved side effects for medicines: " + Arrays.toString(medicineIds));
        return sideEffects;
    }
    
    @Override
    public Map<String, Object> checkMedicineInteractions(String[] medicineIds) {
        if (medicineIds == null || medicineIds.length < 2) {
            return new HashMap<>();
        }
        
        Map<String, Object> interactions = new HashMap<>();
        Map<String, String>[] interactionsArray = new Map[medicineIds.length * (medicineIds.length - 1) / 2];
        int interactionCount = 0;
        
        // Check each pair of medicines for interactions
        for (int i = 0; i < medicineIds.length; i++) {
            for (int j = i + 1; j < medicineIds.length; j++) {
                String medicine1 = medicineIds[i];
                String medicine2 = medicineIds[j];
                
                // Check if there's an interaction between these medicines
                Map<String, String> medicine1Interactions = medicineInteractionsMap.getOrDefault(medicine1, new HashMap<>());
                if (medicine1Interactions.containsKey(medicine2)) {
                    Map<String, String> interaction = new HashMap<>();
                    interaction.put("medicine1", medicine1);
                    interaction.put("medicine2", medicine2);
                    interaction.put("interaction", medicine1Interactions.get(medicine2));
                    interaction.put("severity", "high");
                    
                    interactionsArray[interactionCount++] = interaction;
                }
            }
        }
        
        // Trim the array to actual size
        Map<String, String>[] trimmedInteractions = new Map[interactionCount];
        System.arraycopy(interactionsArray, 0, trimmedInteractions, 0, interactionCount);
        
        interactions.put("interactions", trimmedInteractions);
        interactions.put("hasInteractions", interactionCount > 0);
        
        logger.info("Checked interactions for medicines: " + Arrays.toString(medicineIds));
        return interactions;
    }
    
    @Override
    public String[] getHealthAdvice(String condition) {
        if (condition == null || condition.isEmpty()) {
            return new String[0];
        }
        
        String normalizedCondition = condition.toLowerCase().trim();
        String[] advice = healthAdviceMap.getOrDefault(normalizedCondition, new String[0]);
        
        logger.info("Retrieved health advice for condition: " + condition);
        return advice;
    }
    
    @Override
    public String getSuggestedDosage(String medicineId, int age, double weight) {
        if (medicineId == null || medicineId.isEmpty() || age <= 0 || weight <= 0) {
            return "No dosage information available.";
        }
        
        // This is a simplified dosage calculation for demonstration
        // In a real system, this would be based on medical guidelines
        
        String ageGroup;
        if (age < 2) {
            ageGroup = "infant";
        } else if (age < 12) {
            ageGroup = "child";
        } else if (age < 18) {
            ageGroup = "adolescent";
        } else if (age < 65) {
            ageGroup = "adult";
        } else {
            ageGroup = "senior";
        }
        
        // Simulated dosage calculation
        String dosage = "For " + ageGroup + " (" + age + " years, " + weight + " kg): ";
        
        switch (medicineId) {
            case "MED001": // Paracetamol
                dosage += calculatePainkilerDosage(age, weight);
                break;
            case "MED002": // Cetirizine
                dosage += calculateAntihistamineDosage(age, weight);
                break;
            default:
                dosage += "Please consult a healthcare professional for dosage information.";
        }
        
        logger.info("Calculated dosage for medicine: " + medicineId);
        return dosage;
    }
    
    private String normalizeSymptom(String symptom) {
        // Convert to lowercase and trim whitespace
        return symptom.toLowerCase().trim();
    }
    
    // Method to calculate painkiller dosage
    private String calculatePainkilerDosage(int age, double weight) {
        if (age < 12) {
            return Math.round(weight * 10) + "mg every 4-6 hours (max 4 doses in 24 hours)";
        } else {
            return "500-1000mg every 4-6 hours (max 4g in 24 hours)";
        }
    }
    
    // Method to calculate antihistamine dosage
    private String calculateAntihistamineDosage(int age, double weight) {
        if (age < 6) {
            return "Not recommended for children under 6";
        } else if (age < 12) {
            return "5mg once daily";
        } else {
            return "10mg once daily";
        }
    }
    
    private void initializeSymptomMedicineMap() {
        symptomMedicineMap = new HashMap<>();
        
        // Create some sample medicines
        Medicine paracetamol = new Medicine("MED001", "Paracetamol", 5.99, false);
        paracetamol.setDescription("Pain reliever and fever reducer");
        paracetamol.setCategory("OTC");
        
        Medicine cetirizine = new Medicine("MED002", "Cetirizine", 8.99, false);
        cetirizine.setDescription("Antihistamine for allergy relief");
        cetirizine.setCategory("OTC");
        
        Medicine ibuprofen = new Medicine("MED003", "Ibuprofen", 6.99, false);
        ibuprofen.setDescription("NSAID for pain and inflammation");
        ibuprofen.setCategory("OTC");
        
        Medicine pseudoephedrine = new Medicine("MED004", "Pseudoephedrine", 9.99, false);
        pseudoephedrine.setDescription("Decongestant for nasal congestion");
        pseudoephedrine.setCategory("OTC");
        
        // Map symptoms to medicines
        symptomMedicineMap.put("headache", new Medicine[]{paracetamol, ibuprofen});
        symptomMedicineMap.put("fever", new Medicine[]{paracetamol, ibuprofen});
        symptomMedicineMap.put("pain", new Medicine[]{paracetamol, ibuprofen});
        symptomMedicineMap.put("allergies", new Medicine[]{cetirizine});
        symptomMedicineMap.put("sneezing", new Medicine[]{cetirizine});
        symptomMedicineMap.put("runny nose", new Medicine[]{cetirizine, pseudoephedrine});
        symptomMedicineMap.put("congestion", new Medicine[]{pseudoephedrine});
        symptomMedicineMap.put("sinus pressure", new Medicine[]{pseudoephedrine, ibuprofen});
        symptomMedicineMap.put("inflammation", new Medicine[]{ibuprofen});
    }
    
    private void initializeSymptomInfoMap() {
        symptomInfoMap = new HashMap<>();
        
        // Headache information
        Map<String, Object> headacheInfo = new HashMap<>();
        headacheInfo.put("description", "Pain or discomfort in the head, scalp, or neck");
        headacheInfo.put("precautions", new String[]{
            "Rest in a quiet, dark room",
            "Apply a cold compress to your forehead",
            "Stay hydrated",
            "Avoid triggers such as loud noises or bright lights"
        });
        symptomInfoMap.put("headache", headacheInfo);
        
        // Fever information
        Map<String, Object> feverInfo = new HashMap<>();
        feverInfo.put("description", "Elevated body temperature above the normal range");
        feverInfo.put("precautions", new String[]{
            "Rest and get plenty of fluids",
            "Dress lightly",
            "Take a lukewarm bath",
            "See a doctor if fever persists over 3 days or exceeds 103°F (39.4°C)"
        });
        symptomInfoMap.put("fever", feverInfo);
        
        // Other symptoms...
    }
    
    private void initializeMedicineInteractionsMap() {
        medicineInteractionsMap = new HashMap<>();
        
        // Create some sample interactions
        Map<String, String> paracetamolInteractions = new HashMap<>();
        paracetamolInteractions.put("MED005", "May increase risk of bleeding");
        medicineInteractionsMap.put("MED001", paracetamolInteractions);
        
        Map<String, String> ibuprofenInteractions = new HashMap<>();
        ibuprofenInteractions.put("MED005", "May increase risk of bleeding");
        ibuprofenInteractions.put("MED006", "May decrease effectiveness of blood pressure medication");
        medicineInteractionsMap.put("MED003", ibuprofenInteractions);
    }
    
    private void initializeCriticalSymptoms() {
        criticalSymptoms = new String[] {
            "chest pain",
            "difficulty breathing",
            "severe headache",
            "sudden dizziness",
            "confusion",
            "slurred speech",
            "severe abdominal pain",
            "high fever with stiff neck",
            "severe vomiting",
            "suicidal thoughts"
        };
    }
    
    private void initializeSymptomSpecialistMap() {
        symptomSpecialistMap = new HashMap<>();
        
        // Headache specialists
        Map<String, Double> headacheSpecialists = new HashMap<>();
        headacheSpecialists.put("Neurologist", 0.8);
        headacheSpecialists.put("General Practitioner", 0.6);
        symptomSpecialistMap.put("headache", headacheSpecialists);
        
        // Allergy specialists
        Map<String, Double> allergySpecialists = new HashMap<>();
        allergySpecialists.put("Allergist", 0.9);
        allergySpecialists.put("ENT Specialist", 0.7);
        allergySpecialists.put("General Practitioner", 0.5);
        symptomSpecialistMap.put("allergies", allergySpecialists);
        
        // Congestion specialists
        Map<String, Double> congestionSpecialists = new HashMap<>();
        congestionSpecialists.put("ENT Specialist", 0.8);
        congestionSpecialists.put("Pulmonologist", 0.6);
        congestionSpecialists.put("General Practitioner", 0.5);
        symptomSpecialistMap.put("congestion", congestionSpecialists);
        
        // Other symptoms...
    }
    
    private void initializeHealthAdviceMap() {
        healthAdviceMap = new HashMap<>();
        
        healthAdviceMap.put("headache", new String[]{
            "Ensure you're staying hydrated",
            "Maintain regular sleep patterns",
            "Practice stress reduction techniques",
            "Limit screen time, especially before bed",
            "Consider tracking triggers in a headache journal"
        });
        
        healthAdviceMap.put("allergies", new String[]{
            "Identify and avoid allergens when possible",
            "Use air purifiers at home",
            "Keep windows closed during high pollen seasons",
            "Wash bedding frequently in hot water",
            "Shower after outdoor activities"
        });
        
        healthAdviceMap.put("common cold", new String[]{
            "Get plenty of rest",
            "Stay hydrated with water, tea, and clear broths",
            "Use a humidifier to add moisture to the air",
            "Gargle with salt water to soothe a sore throat",
            "Consider zinc lozenges within 24 hours of symptoms"
        });
        
        // Other health advice...
    }
    
    private void initializeMedicineSideEffectsMap() {
        medicineSideEffectsMap = new HashMap<>();
        
        medicineSideEffectsMap.put("MED001", new String[]{
            "Nausea",
            "Stomach pain",
            "Possible liver damage with prolonged use or high doses"
        });
        
        medicineSideEffectsMap.put("MED002", new String[]{
            "Drowsiness",
            "Dry mouth",
            "Fatigue",
            "Headache"
        });
        
        medicineSideEffectsMap.put("MED003", new String[]{
            "Stomach upset",
            "Heartburn",
            "Dizziness",
            "Possible increased risk of heart problems with prolonged use"
        });
        
        medicineSideEffectsMap.put("MED004", new String[]{
            "Nervousness",
            "Difficulty sleeping",
            "Increased blood pressure",
            "Rapid heartbeat"
        });
    }
} 