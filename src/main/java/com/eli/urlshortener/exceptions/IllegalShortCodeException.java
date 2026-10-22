package com.eli.urlshortener.exceptions;

public class IllegalShortCodeException extends RuntimeException {
    public IllegalShortCodeException(String message) {
        super(message);
    }
}
