package com.eli.urlshortener.email;

/**
 * This interface is an abstraction of notification services in the application
 * This interface should be extended by other more specific types of notification services(email, phone, etc.)
 */
public interface NotificationService {
    /**
     * This method sends an account creation notification
     * @param emailNotification the details of the user and the required template variables
     */
    void accCreatedNotification(EmailNotification emailNotification);
    /**
     * This method sends a url creation notification
     * @param emailNotification the details of the user and the required template variables
     */
    void urlCreatedNotification(EmailNotification emailNotification);
}
