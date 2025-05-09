package com.pharmacy.service;

import com.pharmacy.model.Medicine;
import java.util.List;
import java.util.Map;

/**
 * Interface for the AI-powered medicine recommendation system
 */
public interface MedicineRecommendationSystem {
    
    /**
     * Get recommended over-the-counter medicines based on symptoms
     * 
     * @param symptoms List of symptoms
     * @return List of recommended medicines
     */
    List<Medicine> recommendMedicinesForSymptoms(List<String> symptoms);
    
    /**
     * Get detailed information about a symptom
     * 
     * @param symptom The symptom to get information about
     * @return Map containing symptom information
     */
    Map<String, Object> getSymptomInformation(String symptom);
    
    /**
     * Check if symptoms require medical attention
     * 
     * @param symptoms List of symptoms
     * @return true if medical attention is recommended, false otherwise
     */
    boolean isMedicalAttentionRequired(List<String> symptoms);
    
    /**
     * Get recommended specialists based on symptoms
     * 
     * @param symptoms List of symptoms
     * @return Map of specialist types and their relevance scores
     */
    Map<String, Double> getRecommendedSpecialists(List<String> symptoms);
    
    /**
     * Get precautions for specific symptoms
     * 
     * @param symptoms List of symptoms
     * @return List of precautions to take
     */
    List<String> getPrecautionsForSymptoms(List<String> symptoms);
    
    /**
     * Get potential side effects of recommended medicines
     * 
     * @param medicineIds List of medicine IDs
     * @return Map of medicine IDs and their side effects
     */
    Map<String, List<String>> getPotentialSideEffects(List<String> medicineIds);
    
    /**
     * Check for medicine interactions
     * 
     * @param medicineIds List of medicine IDs to check for interactions
     * @return Map of interaction information
     */
    Map<String, Object> checkMedicineInteractions(List<String> medicineIds);
    
    /**
     * Provide health advice for specific conditions
     * 
     * @param condition The health condition
     * @return List of advice
     */
    List<String> getHealthAdvice(String condition);
    
    /**
     * Get suggested dosage for an OTC medicine based on age and weight
     * 
     * @param medicineId The medicine ID
     * @param age The age of the patient
     * @param weight The weight of the patient in kg
     * @return The suggested dosage
     */
    String getSuggestedDosage(String medicineId, int age, double weight);
} 