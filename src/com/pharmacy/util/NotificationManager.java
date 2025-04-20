package com.pharmacy.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages notifications using multithreading
 */
public class NotificationManager {
    
    private static final Logger logger = Logger.getLogger(NotificationManager.class.getName());
    
    // Thread pool for sending notifications
    // Exposed as public for demonstration purposes only
    public final ExecutorService notificationExecutor;
    
    // Singleton instance
    private static NotificationManager instance;
    
    /**
     * Private constructor for singleton pattern
     */
    private NotificationManager() {
        this.notificationExecutor = Executors.newFixedThreadPool(5);
    }
    
    /**
     * Get the singleton instance
     * 
     * @return The NotificationManager instance
     */
    public static synchronized NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }
    
    /**
     * Send an email notification
     * 
     * @param recipient The recipient email address
     * @param subject The email subject
     * @param message The email message
     */
    public void sendEmailNotification(String recipient, String subject, String message) {
        notificationExecutor.submit(new EmailNotificationTask(recipient, subject, message));
    }
    
    /**
     * Send an SMS notification
     * 
     * @param phoneNumber The recipient phone number
     * @param message The SMS message
     */
    public void sendSMSNotification(String phoneNumber, String message) {
        notificationExecutor.submit(new SMSNotificationTask(phoneNumber, message));
    }
    
    /**
     * Send a push notification
     * 
     * @param deviceToken The recipient device token
     * @param title The notification title
     * @param message The notification message
     * @param data Additional data for the notification
     */
    public void sendPushNotification(String deviceToken, String title, String message, String data) {
        notificationExecutor.submit(new PushNotificationTask(deviceToken, title, message, data));
    }
    
    /**
     * Schedule a reminder notification
     * 
     * @param recipient The recipient (email or phone)
     * @param message The reminder message
     * @param delayHours The delay in hours before sending
     * @param notificationType The type of notification (email, sms, push)
     */
    public void scheduleReminder(String recipient, String message, int delayHours, String notificationType) {
        notificationExecutor.submit(new ReminderTask(recipient, message, delayHours, notificationType));
    }
    
    /**
     * Send a batch of notifications
     * 
     * @param recipients Array of recipients
     * @param subject The notification subject
     * @param message The notification message
     * @param notificationType The type of notification (email, sms, push)
     */
    public void sendBatchNotifications(String[] recipients, String subject, String message, String notificationType) {
        for (String recipient : recipients) {
            if ("email".equalsIgnoreCase(notificationType)) {
                sendEmailNotification(recipient, subject, message);
            } else if ("sms".equalsIgnoreCase(notificationType)) {
                sendSMSNotification(recipient, message);
            } else if ("push".equalsIgnoreCase(notificationType)) {
                sendPushNotification(recipient, subject, message, null);
            }
        }
    }
    
    /**
     * Submit a custom notification task to the executor
     * 
     * @param task The task to submit
     */
    public void submitCustomNotificationTask(Runnable task) {
        notificationExecutor.submit(task);
    }
    
    /**
     * Shut down the notification manager
     * 
     * @param waitForCompletion Whether to wait for pending notifications to complete
     */
    public void shutdown(boolean waitForCompletion) {
        notificationExecutor.shutdown();
        
        if (waitForCompletion) {
            try {
                if (!notificationExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                    notificationExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                notificationExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        } else {
            notificationExecutor.shutdownNow();
        }
    }
    
    /**
     * Task for sending email notifications
     */
    private class EmailNotificationTask implements Runnable {
        private final String recipient;
        private final String subject;
        private final String message;
        
        public EmailNotificationTask(String recipient, String subject, String message) {
            this.recipient = recipient;
            this.subject = subject;
            this.message = message;
        }
        
        @Override
        public void run() {
            try {
                // In a real implementation, this would use an email service
                // For now, we'll just log that the email would be sent
                logger.info("Sending email to " + recipient + " with subject: " + subject);
                // Simulate email sending delay
                Thread.sleep(500);
                logger.info("Email sent to " + recipient);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Email notification interrupted", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error sending email notification", e);
            }
        }
    }
    
    /**
     * Task for sending SMS notifications
     */
    private class SMSNotificationTask implements Runnable {
        private final String phoneNumber;
        private final String message;
        
        public SMSNotificationTask(String phoneNumber, String message) {
            this.phoneNumber = phoneNumber;
            this.message = message;
        }
        
        @Override
        public void run() {
            try {
                // In a real implementation, this would use an SMS service
                // For now, we'll just log that the SMS would be sent
                logger.info("Sending SMS to " + phoneNumber + ": " + message);
                // Simulate SMS sending delay
                Thread.sleep(300);
                logger.info("SMS sent to " + phoneNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "SMS notification interrupted", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error sending SMS notification", e);
            }
        }
    }
    
    /**
     * Task for sending push notifications
     */
    private class PushNotificationTask implements Runnable {
        private final String deviceToken;
        private final String title;
        private final String message;
        private final String data;
        
        public PushNotificationTask(String deviceToken, String title, String message, String data) {
            this.deviceToken = deviceToken;
            this.title = title;
            this.message = message;
            this.data = data;
        }
        
        @Override
        public void run() {
            try {
                // In a real implementation, this would use a push notification service
                // For now, we'll just log that the push notification would be sent
                logger.info("Sending push notification to device " + deviceToken + ": " + title);
                // Simulate push notification sending delay
                Thread.sleep(200);
                logger.info("Push notification sent to device " + deviceToken);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Push notification interrupted", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error sending push notification", e);
            }
        }
    }
    
    /**
     * Task for scheduling reminder notifications
     */
    private class ReminderTask implements Runnable {
        private final String recipient;
        private final String message;
        private final int delayHours;
        private final String notificationType;
        
        public ReminderTask(String recipient, String message, int delayHours, String notificationType) {
            this.recipient = recipient;
            this.message = message;
            this.delayHours = delayHours;
            this.notificationType = notificationType;
        }
        
        @Override
        public void run() {
            try {
                logger.info("Scheduling " + notificationType + " reminder for " + recipient + " in " + delayHours + " hours");
                
                // In a real implementation, this would use a scheduling service
                // For demo purposes, we'll just sleep for a few seconds instead of hours
                Thread.sleep(delayHours * 1000);
                
                // Send the notification based on the type
                if ("email".equalsIgnoreCase(notificationType)) {
                    sendEmailNotification(recipient, "Medication Reminder", message);
                } else if ("sms".equalsIgnoreCase(notificationType)) {
                    sendSMSNotification(recipient, message);
                } else if ("push".equalsIgnoreCase(notificationType)) {
                    sendPushNotification(recipient, "Medication Reminder", message, null);
                }
                
                logger.info("Reminder sent to " + recipient);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Reminder task interrupted", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error in reminder task", e);
            }
        }
    }
} 