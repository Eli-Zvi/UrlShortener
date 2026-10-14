package com.eli.urlshortener.advices;

import com.eli.urlshortener.exceptions.UrlAlreadyExistsException;
import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class UrlExceptionHandler {
    @ExceptionHandler({UrlAlreadyExistsException.class, ShortCodeNotFoundException.class})
    public ResponseEntity<String> handleUrlExceptions(RuntimeException ex){
        switch (ex){
            case UrlAlreadyExistsException urlEx ->
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Url Already exists");

            case ShortCodeNotFoundException urlEx ->
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given short code does not exist");

            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
