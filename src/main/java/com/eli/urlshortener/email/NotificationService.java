package com.eli.urlshortener.email;

public interface NotificationService {
    void accCreatedNotification(EmailNotification emailNotification);

    void urlCreatedNotification(EmailNotification emailNotification);
}
