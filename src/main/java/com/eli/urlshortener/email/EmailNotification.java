package com.eli.urlshortener.email;

import java.util.Map;

/**
 * This class represents an email notification to be sent to a user
 * @param username the username of the user
 * @param email the email of the user
 * @param templateVariables the variables to be provided to the template to populate the email
 */
public record EmailNotification(
        String username,
        String email,
        Map<String, String> templateVariables
) {
}
