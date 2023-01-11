package com.example.mmacedoaraujo.registrationapi.handler;

import com.example.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundException;
import com.example.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundExceptionDetails;
import com.example.mmacedoaraujo.registrationapi.exceptions.UserNotFoundExceptionDetails;
import com.example.mmacedoaraujo.registrationapi.exceptions.UserNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<UserNotFoundExceptionDetails> userNotFoundExceptionHandle(UserNotFoundExeption userNotFoundExeption) {
        return new ResponseEntity<>(
                UserNotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("NOT_FOUND")
                        .details(userNotFoundExeption.getMessage())
                        .message(userNotFoundExeption.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND

        );
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<AddressNotFoundExceptionDetails> userNotFoundExceptionHandle(AddressNotFoundException addressNotFoundException) {
        return new ResponseEntity<>(
                AddressNotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("NOT_FOUND")
                        .details(addressNotFoundException.getMessage())
                        .message(addressNotFoundException.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND

        );
    }
}
