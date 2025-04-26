package com.pharmacy.service;

import com.pharmacy.model.Medicine;

/**
 * Simplified interface for the medicine recommendation system
 */
public interface MedicineRecommendationSystem {
    
    /**
     * Get recommended over-the-counter medicines based on symptoms
     * 
     * @param symptoms Array of symptoms
     * @return Array of recommended medicines
     */
    Medicine[] recommendMedicinesForSymptoms(String[] symptoms);
    
    /**
     * Check if symptoms require medical attention
     * 
     * @param symptoms Array of symptoms
     * @return true if medical attention is recommended, false otherwise
     */
    boolean isMedicalAttentionRequired(String[] symptoms);
    
    /**
     * Get precautions for specific symptoms
     * 
     * @param symptoms Array of symptoms
     * @return Array of precautions to take
     */
    String[] getPrecautionsForSymptoms(String[] symptoms);
} 