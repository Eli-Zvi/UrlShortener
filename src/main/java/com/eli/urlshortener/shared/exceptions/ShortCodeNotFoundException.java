package com.eli.urlshortener.shared.exceptions;

public class ShortCodeNotFoundException extends RuntimeException {
    public ShortCodeNotFoundException(String message) {
        super(message);
    }
}
