package com.pharmacy.service;

import com.pharmacy.model.Medicine;

public interface MedicineRecommendationSystem {
    

    Medicine[] recommendMedicinesForSymptoms(String[] symptoms);

    boolean isMedicalAttentionRequired(String[] symptoms);

    String[] getPrecautionsForSymptoms(String[] symptoms);
} 