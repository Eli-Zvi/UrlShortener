package com.eli.urlshortener.exceptions;

public class UnsafeUrlException extends RuntimeException {
    public UnsafeUrlException(String message) {
        super(message);
    }
}
