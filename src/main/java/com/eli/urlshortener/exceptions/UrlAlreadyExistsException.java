package com.eli.urlshortener.exceptions;

public class UrlAlreadyExistsException extends RuntimeException {
    public UrlAlreadyExistsException(String message) {
        super(message);
    }
}
