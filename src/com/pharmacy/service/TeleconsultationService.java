package com.pharmacy.service;

import com.pharmacy.model.Doctor;
import com.pharmacy.model.Customer;
import com.pharmacy.model.Prescription;
import java.util.Date;

/**
 * Interface for teleconsultation services
 */
public interface TeleconsultationService {
    
    /**
     * Book a teleconsultation appointment
     * 
     * @param customerId The ID of the customer
     * @param doctorId The ID of the doctor
     * @param appointmentDate The date and time of the appointment
     * @param reason The reason for the consultation
     * @return The appointment ID
     */
    String bookAppointment(String customerId, String doctorId, Date appointmentDate, String reason);
    
    /**
     * Cancel a teleconsultation appointment
     * 
     * @param appointmentId The ID of the appointment to cancel
     * @param cancellationReason The reason for cancellation
     * @return true if cancelled successfully, false otherwise
     */
    boolean cancelAppointment(String appointmentId, String cancellationReason);
    
    /**
     * Reschedule a teleconsultation appointment
     * 
     * @param appointmentId The ID of the appointment to reschedule
     * @param newAppointmentDate The new date and time of the appointment
     * @return true if rescheduled successfully, false otherwise
     */
    boolean rescheduleAppointment(String appointmentId, Date newAppointmentDate);
    
    /**
     * Get available doctors for teleconsultation
     * 
     * @param specialization Optional specialization filter
     * @return Array of available doctors
     */
    Doctor[] getAvailableDoctors(String specialization);
    
    /**
     * Get available time slots for a doctor
     * 
     * @param doctorId The ID of the doctor
     * @param date The date to check availability
     * @return Array of available time slots
     */
    String[] getAvailableTimeSlots(String doctorId, Date date);
    
    /**
     * Start a teleconsultation session
     * 
     * @param appointmentId The ID of the appointment
     * @return URL or session ID for the teleconsultation
     */
    String startConsultation(String appointmentId);
    
    /**
     * End a teleconsultation session
     * 
     * @param sessionId The ID of the teleconsultation session
     * @param duration The duration of the session in minutes
     * @return true if ended successfully, false otherwise
     */
    boolean endConsultation(String sessionId, int duration);
    
    /**
     * Generate a prescription after consultation
     * 
     * @param doctorId The ID of the doctor
     * @param customerId The ID of the customer
     * @param diagnosis The diagnosis
     * @param medicineIds Array of medicine IDs to prescribe
     * @return The generated prescription
     */
    Prescription generatePrescription(String doctorId, String customerId, String diagnosis, String[] medicineIds);
    
    /**
     * Get consultation history for a customer
     * 
     * @param customerId The ID of the customer
     * @return Array of past consultation details
     */
    Object[] getConsultationHistory(String customerId);
    
    /**
     * Get consultation history for a doctor
     * 
     * @param doctorId The ID of the doctor
     * @return Array of past consultation details
     */
    Object[] getDoctorConsultationHistory(String doctorId);
    
    /**
     * Rate a teleconsultation experience
     * 
     * @param appointmentId The ID of the appointment
     * @param rating The rating (usually 1-5)
     * @param feedback Feedback comments
     * @return true if rating submitted successfully, false otherwise
     */
    boolean rateConsultation(String appointmentId, int rating, String feedback);
} 