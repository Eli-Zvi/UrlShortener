package com.eli.urlshortener.advices;

import com.eli.urlshortener.exceptions.UrlAlreadyExistsException;
import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UrlExceptionHandler { //TODO: Capture validation related errors
    @ExceptionHandler({UrlAlreadyExistsException.class, ShortCodeNotFoundException.class})
    public ProblemDetail handleUrlExceptions(RuntimeException ex){
        ProblemDetail defaultResponse = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        switch (ex){
            case UrlAlreadyExistsException urlEx -> {
                return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, urlEx.getMessage());
            }

            case ShortCodeNotFoundException urlEx -> {
                return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, urlEx.getMessage());
            }

            default -> {
                return defaultResponse;
            }
        }
    }
}
