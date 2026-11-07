package com.eli.urlshortener.email;

import java.util.Map;

public record EmailNotification(
        String username,
        String email,
        Map<String, String> templateVariables
) {
}
