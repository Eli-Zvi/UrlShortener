package com.eli.urlshortener.exceptions;

public class UsernameEmailAlreadyExistsException extends RuntimeException {
    public UsernameEmailAlreadyExistsException(String message) {
        super(message);
    }
}
