package com.pharmacy.service;

import com.pharmacy.model.Prescription;
import com.pharmacy.exception.PrescriptionException;
import java.io.File;


public interface PrescriptionService {

    Prescription uploadPrescription(String customerId, File prescriptionFile) throws PrescriptionException;

    Prescription verifyPrescription(String prescriptionId, String verifiedByUserId, String comments) throws PrescriptionException;

    Prescription getPrescription(String prescriptionId) throws PrescriptionException;

    Prescription[] getCustomerPrescriptions(String customerId);

    Prescription[] getValidPrescriptions(String customerId);

    boolean isValidPrescriptionForMedicine(String prescriptionId, String medicineId) throws PrescriptionException;

    Prescription createDigitalPrescription(Prescription prescription) throws PrescriptionException;

    boolean deletePrescription(String prescriptionId) throws PrescriptionException;
} 