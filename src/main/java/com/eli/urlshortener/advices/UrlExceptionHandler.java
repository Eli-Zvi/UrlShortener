package com.eli.urlshortener.advices;

import com.eli.urlshortener.exceptions.IllegalShortCodeException;
import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.exceptions.UrlAlreadyExistsException;
import com.eli.urlshortener.exceptions.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UrlExceptionHandler { //TODO: Capture validation related errors
    @ExceptionHandler({UrlAlreadyExistsException.class, ShortCodeNotFoundException.class})
    public ProblemDetail handleUrlExceptions(RuntimeException ex){
        ProblemDetail defaultResponse = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

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
            message.delete(message.length() - 2, message.length() - 1);
        }

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message.toString());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ProblemDetail handleUsernameExistsException(UsernameAlreadyExistsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalShortCodeException.class)
    public ProblemDetail handleBase62Error(IllegalShortCodeException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
