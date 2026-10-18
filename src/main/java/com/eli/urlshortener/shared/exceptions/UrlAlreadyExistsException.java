package com.eli.urlshortener.shared.exceptions;

public class UrlAlreadyExistsException extends RuntimeException {
    public UrlAlreadyExistsException(String message) {
        super(message);
    }
}
