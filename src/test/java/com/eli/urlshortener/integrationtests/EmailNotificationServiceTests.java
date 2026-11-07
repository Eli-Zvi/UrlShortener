package com.eli.urlshortener.integrationtests;

import com.eli.urlshortener.email.EmailNotification;
import com.eli.urlshortener.email.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;

@SpringBootTest
public class EmailNotificationServiceTests {
    @Autowired
    private NotificationService notificationService;

    @Test
    void test1(){
        notificationService.
                accCreatedNotification(
                        new EmailNotification("abc",
                                "chainthrash@gmail.com",
                                null)
                );

    }

    @Test
    void test2(){
        notificationService.
                urlCreatedNotification(new EmailNotification("abc",
                        "chainthrash@gmail.com",
                        Map.of(
                                "originalUrl", "youtube.com",
                                "shortUrl", "testUrl"
                        )
                ));
    }
}
