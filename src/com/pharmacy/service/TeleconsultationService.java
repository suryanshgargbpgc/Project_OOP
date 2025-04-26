package com.pharmacy.service;

import com.pharmacy.model.Doctor;
import com.pharmacy.model.Customer;
import com.pharmacy.model.Prescription;
import java.util.Date;

public interface TeleconsultationService {

    String bookAppointment(String customerId, String doctorId, Date appointmentDate, String reason);

    boolean cancelAppointment(String appointmentId, String cancellationReason);

    boolean rescheduleAppointment(String appointmentId, Date newAppointmentDate);

    Doctor[] getAvailableDoctors(String specialization);

    String[] getAvailableTimeSlots(String doctorId, Date date);

    String startConsultation(String appointmentId);

    boolean endConsultation(String sessionId, int duration);

    Prescription generatePrescription(String doctorId, String customerId, String diagnosis, String[] medicineIds);

    Object[] getConsultationHistory(String customerId);

    Object[] getDoctorConsultationHistory(String doctorId);

    boolean rateConsultation(String appointmentId, int rating, String feedback);
} 