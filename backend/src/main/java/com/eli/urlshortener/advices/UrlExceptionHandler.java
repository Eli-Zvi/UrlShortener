package com.eli.urlshortener.advices;

import com.eli.urlshortener.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception handler for the Url and UrlAnalytics applications
 * @author Ilay Zvi
 * TODO: Separate user authentication and registration errors to a different exception handler
 */
@RestControllerAdvice
public class UrlExceptionHandler {

    /**
     * Intercepts and handles UrlAlreadyExistsExceptions
     * @param ex - thrown UrlAlreadyExistsException
     * @return ProblemDetail with CONFLICT status and a description of the error
     */
    @ExceptionHandler(UrlAlreadyExistsException.class)
    public ProblemDetail handleUrlExceptions(UrlAlreadyExistsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Intercepts and handles MethodArgumentNotValidException
     * @param ex - thrown MethodArgumentNotValidException
     * @return ProblemDetail with BAD_REQUEST status and a description of the error with the results of the binding result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder message = new StringBuilder();

        if(bindingResult.hasErrors()){
            for (FieldError fieldError : bindingResult.getFieldErrors()){
                message.append("Field:")
                        .append(fieldError.getField())
                        .append(" with value:")
                        .append(fieldError.getRejectedValue())
                        .append(" is invalid - ")
                        .append(fieldError.getDefaultMessage())
                        .append(", ");
            }
            message.delete(message.length() - 2, message.length() - 1);//remove extra comma and space at the end
        }

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message.toString());
    }

    /**
     * Intercepts and handles UsernameEmailAlreadyExistsException
     * @param ex - thrown UsernameEmailAlreadyExistsException
     * @return ProblemDetail with CONFLICT status and a description of the error
     */
    @ExceptionHandler(UsernameEmailAlreadyExistsException.class)
    public ProblemDetail handleUsernameEmailExistsException(UsernameEmailAlreadyExistsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Intercepts and handles IllegalShortCodeException, ShortCodeNotFoundException
     * @param ex - thrown IllegalShortCodeException/ShortCodeNotFoundException
     * @return ProblemDetail with BAD_REQUEST or NOT_FOUND status and a description of the error
     */
    @ExceptionHandler({IllegalShortCodeException.class, ShortCodeNotFoundException.class})
    public ProblemDetail handleShortCodeError(RuntimeException ex){
        switch (ex){
            case IllegalShortCodeException urlEx -> {
                return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, urlEx.getMessage());
            }

            case ShortCodeNotFoundException urlEx -> {
                return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, urlEx.getMessage());
            }

            default -> {
                return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @ExceptionHandler(UnsafeUrlException.class)
    public ProblemDetail handleUnsafeUrl(UnsafeUrlException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }
}
