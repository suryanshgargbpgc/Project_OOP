package com.pharmacy.service.impl;

import com.pharmacy.model.Medicine;
import com.pharmacy.service.MedicineRecommendationSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * Implementation of MedicineRecommendationSystem interface
 * This class provides AI-powered recommendations for OTC medicines based on symptoms
 */
public class MedicineRecommendationSystemImpl implements MedicineRecommendationSystem {
    
    private static final Logger logger = Logger.getLogger(MedicineRecommendationSystemImpl.class.getName());
    
    // Database of symptoms and associated medicines (simulated)
    private Map<String, List<Medicine>> symptomMedicineMap;
    
    // Database of symptom information (simulated)
    private Map<String, Map<String, Object>> symptomInfoMap;
    
    // Database of medicine interactions (simulated)
    private Map<String, Map<String, String>> medicineInteractionsMap;
    
    // List of symptoms that require medical attention (simulated)
    private List<String> criticalSymptoms;
    
    // Map of specialist recommendations by symptom (simulated)
    private Map<String, Map<String, Double>> symptomSpecialistMap;
    
    // Map of health advice by condition (simulated)
    private Map<String, List<String>> healthAdviceMap;
    
    // Database of medicine side effects (simulated)
    private Map<String, List<String>> medicineSideEffectsMap;
    
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
    public List<Medicine> recommendMedicinesForSymptoms(List<String> symptoms) {
        if (symptoms == null || symptoms.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Map to count the occurrence of each medicine
        Map<String, Medicine> recommendedMedicines = new HashMap<>();
        Map<String, Integer> medicineOccurrences = new HashMap<>();
        
        // For each symptom, add its associated medicines to the recommendation map
        for (String symptom : symptoms) {
            String normalizedSymptom = normalizeSymptom(symptom);
            List<Medicine> medicinesForSymptom = symptomMedicineMap.getOrDefault(normalizedSymptom, new ArrayList<>());
            
            for (Medicine medicine : medicinesForSymptom) {
                recommendedMedicines.put(medicine.getMedicineId(), medicine);
                medicineOccurrences.put(
                    medicine.getMedicineId(), 
                    medicineOccurrences.getOrDefault(medicine.getMedicineId(), 0) + 1
                );
            }
        }
        
        // Sort medicines by number of matching symptoms
        List<Medicine> sortedMedicines = new ArrayList<>(recommendedMedicines.values());
        sortedMedicines.sort((m1, m2) -> 
            medicineOccurrences.get(m2.getMedicineId()) - medicineOccurrences.get(m1.getMedicineId())
        );
        
        logger.info("Recommended " + sortedMedicines.size() + " medicines for symptoms: " + symptoms);
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
    public boolean isMedicalAttentionRequired(List<String> symptoms) {
        if (symptoms == null || symptoms.isEmpty()) {
            return false;
        }
        
        for (String symptom : symptoms) {
            String normalizedSymptom = normalizeSymptom(symptom);
            if (criticalSymptoms.contains(normalizedSymptom)) {
                logger.warning("Medical attention required for symptom: " + symptom);
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public Map<String, Double> getRecommendedSpecialists(List<String> symptoms) {
        if (symptoms == null || symptoms.isEmpty()) {
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
        
        logger.info("Recommended specialists for symptoms: " + symptoms);
        return specialistScores;
    }
    
    @Override
    public List<String> getPrecautionsForSymptoms(List<String> symptoms) {
        if (symptoms == null || symptoms.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String> precautions = new ArrayList<>();
        
        for (String symptom : symptoms) {
            String normalizedSymptom = normalizeSymptom(symptom);
            Map<String, Object> info = symptomInfoMap.getOrDefault(normalizedSymptom, new HashMap<>());
            
            if (info.containsKey("precautions")) {
                @SuppressWarnings("unchecked")
                List<String> symptomPrecautions = (List<String>) info.get("precautions");
                precautions.addAll(symptomPrecautions);
            }
        }
        
        // Remove duplicates
        return precautions.stream().distinct().collect(Collectors.toList());
    }
    
    @Override
    public Map<String, List<String>> getPotentialSideEffects(List<String> medicineIds) {
        if (medicineIds == null || medicineIds.isEmpty()) {
            return new HashMap<>();
        }
        
        Map<String, List<String>> sideEffects = new HashMap<>();
        
        for (String medicineId : medicineIds) {
            List<String> effects = medicineSideEffectsMap.getOrDefault(medicineId, new ArrayList<>());
            sideEffects.put(medicineId, effects);
        }
        
        logger.info("Retrieved side effects for medicines: " + medicineIds);
        return sideEffects;
    }
    
    @Override
    public Map<String, Object> checkMedicineInteractions(List<String> medicineIds) {
        if (medicineIds == null || medicineIds.size() < 2) {
            return new HashMap<>();
        }
        
        Map<String, Object> interactions = new HashMap<>();
        List<Map<String, String>> interactionsList = new ArrayList<>();
        
        // Check each pair of medicines for interactions
        for (int i = 0; i < medicineIds.size(); i++) {
            for (int j = i + 1; j < medicineIds.size(); j++) {
                String medicine1 = medicineIds.get(i);
                String medicine2 = medicineIds.get(j);
                
                // Check if there's an interaction between these medicines
                Map<String, String> medicine1Interactions = medicineInteractionsMap.getOrDefault(medicine1, new HashMap<>());
                if (medicine1Interactions.containsKey(medicine2)) {
                    Map<String, String> interaction = new HashMap<>();
                    interaction.put("medicine1", medicine1);
                    interaction.put("medicine2", medicine2);
                    interaction.put("description", medicine1Interactions.get(medicine2));
                    interaction.put("severity", "moderate"); // Simplified - real system would have severity levels
                    
                    interactionsList.add(interaction);
                }
            }
        }
        
        interactions.put("hasInteractions", !interactionsList.isEmpty());
        interactions.put("interactions", interactionsList);
        
        logger.info("Checked interactions for medicines: " + medicineIds);
        return interactions;
    }
    
    @Override
    public List<String> getHealthAdvice(String condition) {
        if (condition == null || condition.isEmpty()) {
            return new ArrayList<>();
        }
        
        String normalizedCondition = normalizeSymptom(condition);
        List<String> advice = healthAdviceMap.getOrDefault(normalizedCondition, new ArrayList<>());
        
        logger.info("Retrieved health advice for condition: " + condition);
        return advice;
    }
    
    @Override
    public String getSuggestedDosage(String medicineId, int age, double weight) {
        if (medicineId == null || medicineId.isEmpty() || age <= 0 || weight <= 0) {
            return "Invalid input parameters";
        }
        
        // This is a simplified dosage recommendation logic
        // In a real system, this would involve complex rules and medical guidelines
        
        // For demonstration purposes, we'll return a generic dosage recommendation
        if (age < 2) {
            return "Not recommended for children under 2 years of age. Consult a pediatrician.";
        } else if (age < 12) {
            return "Children's dosage: 5ml (1 teaspoon) every 6-8 hours as needed. Do not exceed 4 doses in 24 hours.";
        } else if (age < 18) {
            return "Teen dosage: 10ml (2 teaspoons) every 6-8 hours as needed. Do not exceed 4 doses in 24 hours.";
        } else {
            return "Adult dosage: 15-30ml (3-6 teaspoons) every 6-8 hours as needed. Do not exceed 4 doses in 24 hours.";
        }
    }
    
    /**
     * Normalize a symptom string for consistent lookup
     * 
     * @param symptom The symptom to normalize
     * @return Normalized symptom string
     */
    private String normalizeSymptom(String symptom) {
        return symptom.toLowerCase().trim();
    }
    
    /**
     * Initialize the symptom-medicine map with sample data
     */
    private void initializeSymptomMedicineMap() {
        symptomMedicineMap = new HashMap<>();
        
        // Sample medicines
        Medicine paracetamol = new Medicine("MED001", "Paracetamol", 5.99, false);
        paracetamol.setDescription("Pain reliever and fever reducer");
        paracetamol.setCategory("OTC");
        
        Medicine ibuprofen = new Medicine("MED002", "Ibuprofen", 6.99, false);
        ibuprofen.setDescription("Non-steroidal anti-inflammatory drug");
        ibuprofen.setCategory("OTC");
        
        Medicine cetirizine = new Medicine("MED003", "Cetirizine", 8.99, false);
        cetirizine.setDescription("Antihistamine for allergy relief");
        cetirizine.setCategory("OTC");
        
        Medicine loratadine = new Medicine("MED004", "Loratadine", 9.99, false);
        loratadine.setDescription("Non-drowsy antihistamine");
        loratadine.setCategory("OTC");
        
        Medicine guaifenesin = new Medicine("MED005", "Guaifenesin", 7.99, false);
        guaifenesin.setDescription("Expectorant to help loosen congestion");
        guaifenesin.setCategory("OTC");
        
        Medicine dextromethorphan = new Medicine("MED006", "Dextromethorphan", 8.49, false);
        dextromethorphan.setDescription("Cough suppressant");
        dextromethorphan.setCategory("OTC");
        
        Medicine bismuthSubsalicylate = new Medicine("MED007", "Bismuth Subsalicylate", 10.99, false);
        bismuthSubsalicylate.setDescription("Anti-diarrheal and upset stomach reliever");
        bismuthSubsalicylate.setCategory("OTC");
        
        // Map symptoms to medicines
        symptomMedicineMap.put("headache", Arrays.asList(paracetamol, ibuprofen));
        symptomMedicineMap.put("fever", Arrays.asList(paracetamol, ibuprofen));
        symptomMedicineMap.put("pain", Arrays.asList(paracetamol, ibuprofen));
        symptomMedicineMap.put("muscle pain", Arrays.asList(ibuprofen));
        symptomMedicineMap.put("allergy", Arrays.asList(cetirizine, loratadine));
        symptomMedicineMap.put("runny nose", Arrays.asList(cetirizine, loratadine));
        symptomMedicineMap.put("itchy eyes", Arrays.asList(cetirizine, loratadine));
        symptomMedicineMap.put("congestion", Arrays.asList(guaifenesin));
        symptomMedicineMap.put("cough", Arrays.asList(dextromethorphan, guaifenesin));
        symptomMedicineMap.put("diarrhea", Arrays.asList(bismuthSubsalicylate));
        symptomMedicineMap.put("upset stomach", Arrays.asList(bismuthSubsalicylate));
    }
    
    /**
     * Initialize the symptom information map with sample data
     */
    private void initializeSymptomInfoMap() {
        symptomInfoMap = new HashMap<>();
        
        Map<String, Object> headacheInfo = new HashMap<>();
        headacheInfo.put("description", "Pain or discomfort in the head, scalp, or neck");
        headacheInfo.put("possibleCauses", Arrays.asList("Stress", "Tension", "Dehydration", "Migraine", "Sinus pressure"));
        headacheInfo.put("precautions", Arrays.asList("Stay hydrated", "Rest in a quiet, dark room", "Apply cold compress"));
        symptomInfoMap.put("headache", headacheInfo);
        
        Map<String, Object> feverInfo = new HashMap<>();
        feverInfo.put("description", "Body temperature above the normal range of 98-100°F (37°C)");
        feverInfo.put("possibleCauses", Arrays.asList("Infection", "Inflammation", "Immune response"));
        feverInfo.put("precautions", Arrays.asList("Stay hydrated", "Rest", "Use light clothing and bedding"));
        symptomInfoMap.put("fever", feverInfo);
        
        Map<String, Object> coughInfo = new HashMap<>();
        coughInfo.put("description", "Sudden expulsion of air from the lungs to clear the airway");
        coughInfo.put("possibleCauses", Arrays.asList("Common cold", "Allergies", "Asthma", "Infection"));
        coughInfo.put("precautions", Arrays.asList("Stay hydrated", "Use a humidifier", "Avoid irritants"));
        symptomInfoMap.put("cough", coughInfo);
        
        // Add more symptom information as needed
    }
    
    /**
     * Initialize the medicine interactions map with sample data
     */
    private void initializeMedicineInteractionsMap() {
        medicineInteractionsMap = new HashMap<>();
        
        // Example interactions (simplified)
        Map<String, String> paracetamolInteractions = new HashMap<>();
        paracetamolInteractions.put("MED002", "Taking Paracetamol and Ibuprofen together is generally safe when following recommended dosages, but may increase risk of side effects.");
        medicineInteractionsMap.put("MED001", paracetamolInteractions);
        
        Map<String, String> ibuprofenInteractions = new HashMap<>();
        ibuprofenInteractions.put("MED001", "Taking Ibuprofen and Paracetamol together is generally safe when following recommended dosages, but may increase risk of side effects.");
        medicineInteractionsMap.put("MED002", ibuprofenInteractions);
        
        // Add more interactions as needed
    }
    
    /**
     * Initialize the list of symptoms that require medical attention
     */
    private void initializeCriticalSymptoms() {
        criticalSymptoms = Arrays.asList(
            "chest pain",
            "difficulty breathing",
            "severe headache",
            "sudden vision changes",
            "sudden weakness",
            "high fever",
            "uncontrolled bleeding",
            "severe abdominal pain",
            "seizure",
            "unconsciousness"
        );
    }
    
    /**
     * Initialize the symptom-specialist map with sample data
     */
    private void initializeSymptomSpecialistMap() {
        symptomSpecialistMap = new HashMap<>();
        
        Map<String, Double> headacheSpecialists = new HashMap<>();
        headacheSpecialists.put("General Practitioner", 1.0);
        headacheSpecialists.put("Neurologist", 0.7);
        symptomSpecialistMap.put("headache", headacheSpecialists);
        
        Map<String, Double> feverSpecialists = new HashMap<>();
        feverSpecialists.put("General Practitioner", 1.0);
        feverSpecialists.put("Infectious Disease Specialist", 0.5);
        symptomSpecialistMap.put("fever", feverSpecialists);
        
        Map<String, Double> coughSpecialists = new HashMap<>();
        coughSpecialists.put("General Practitioner", 1.0);
        coughSpecialists.put("Pulmonologist", 0.6);
        coughSpecialists.put("ENT Specialist", 0.4);
        symptomSpecialistMap.put("cough", coughSpecialists);
        
        // Add more specialist recommendations as needed
    }
    
    /**
     * Initialize the health advice map with sample data
     */
    private void initializeHealthAdviceMap() {
        healthAdviceMap = new HashMap<>();
        
        healthAdviceMap.put("common cold", Arrays.asList(
            "Rest and stay hydrated",
            "Use a humidifier to add moisture to the air",
            "Gargle with salt water to soothe a sore throat",
            "Take over-the-counter cold medications as directed",
            "Stay home to avoid spreading the cold to others"
        ));
        
        healthAdviceMap.put("allergies", Arrays.asList(
            "Identify and avoid allergy triggers",
            "Keep windows closed during high pollen seasons",
            "Use air purifiers to reduce allergens",
            "Take antihistamines as recommended",
            "Consider nasal irrigation to clear allergens from nasal passages"
        ));
        
        healthAdviceMap.put("headache", Arrays.asList(
            "Rest in a quiet, dark room",
            "Apply a cold or warm compress to your head",
            "Stay hydrated",
            "Practice relaxation techniques",
            "Maintain a regular sleep schedule"
        ));
        
        // Add more health advice as needed
    }
    
    /**
     * Initialize the medicine side effects map with sample data
     */
    private void initializeMedicineSideEffectsMap() {
        medicineSideEffectsMap = new HashMap<>();
        
        medicineSideEffectsMap.put("MED001", Arrays.asList(
            "Nausea",
            "Stomach pain",
            "Liver damage (with excessive use)"
        ));
        
        medicineSideEffectsMap.put("MED002", Arrays.asList(
            "Stomach upset",
            "Heartburn",
            "Stomach bleeding (with long-term use)",
            "Increased blood pressure"
        ));
        
        medicineSideEffectsMap.put("MED003", Arrays.asList(
            "Drowsiness",
            "Dry mouth",
            "Headache"
        ));
        
        medicineSideEffectsMap.put("MED004", Arrays.asList(
            "Headache",
            "Dry mouth",
            "Fatigue"
        ));
        
        // Add more side effects as needed
    }
} 