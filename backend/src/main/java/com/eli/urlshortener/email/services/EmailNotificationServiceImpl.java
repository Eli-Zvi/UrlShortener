package com.eli.urlshortener.email.services;

import com.eli.urlshortener.email.EmailNotificationService;
import com.eli.urlshortener.email.EmailNotification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

/**
 * Email Notification service to notify users of successful new account creation and successful new url creation
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    private final String HOME_PAGE_URL = "https://localhost:8080";

    /**
     * Notifies a user with the given information, subject, and template
     * @param emailNotification the details of the users to send the email to
     * @param subject the subject of the email
     * @param context template context, variables to inject to the template
     * @param template the HTML template to process
     */
    private void notify(EmailNotification emailNotification,
                        String subject,
                        Context context,
                        String template
                        ){
        MimeMessage message = javaMailSender.createMimeMessage();

        String html = templateEngine.process(template, context);

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setText(html, true); //boolean true sets html header instead of plaintext
            messageHelper.setFrom(username); //this may sometimes be necessary to avoid errors
            messageHelper.setTo(emailNotification.email()); //set client email
            messageHelper.setSubject(subject);
            log.info("Sent an email to user: {}", emailNotification.username());
        }catch (MessagingException ignored){
            log.info("Error occurred while sending an email to user: {}", emailNotification.username());
        }

        javaMailSender.send(message);
    }

    /**
     * Asynchronously assemble and notify users of successful account creation
     * @param emailNotification the details of the user and the required template variables
     */
    @Override
    @Async
    public void accCreatedNotification(EmailNotification emailNotification) {
        final String accCreationTemplate = "account-created.html";
        final String subject = String.format("Welcome to UrlShortener %s!", emailNotification.username());

        //Setting template variables
        Context context = new Context();
        context.setVariable("username", emailNotification.username());
        context.setVariable("appUrl", HOME_PAGE_URL);

        this.notify(emailNotification, subject, context, accCreationTemplate);
    }

    /**
     * Asynchronously assemble and notify users of successful url creation
     * @param emailNotification the details of the user and the required template variables
     */
    @Override
    @Async
    public void urlCreatedNotification(EmailNotification emailNotification) {
        final String urlCreationTemplate = "url-created.html";
        final String subject = "Successfully Registered a New Url";

        //Setting template variables
        Context context = new Context();
        context.setVariable("username", emailNotification.username());

        // Insert/set variables into context
        for(Map.Entry<String, String> entry : emailNotification.templateVariables().entrySet()){
            context.setVariable(entry.getKey(), entry.getValue());
        }

        this.notify(emailNotification, subject, context, urlCreationTemplate);
    }
}
