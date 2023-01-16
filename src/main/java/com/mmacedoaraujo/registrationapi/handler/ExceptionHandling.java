package com.mmacedoaraujo.registrationapi.handler;

import com.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundException;
import com.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundExceptionDetails;
import com.mmacedoaraujo.registrationapi.exceptions.PersonNotFoundExceptionDetails;
import com.mmacedoaraujo.registrationapi.exceptions.PersonNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundExeption.class)
    public ResponseEntity<PersonNotFoundExceptionDetails> userNotFoundExceptionHandle(PersonNotFoundExeption personNotFoundExeption) {
        return new ResponseEntity<>(
                PersonNotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("NOT_FOUND")
                        .details(personNotFoundExeption.getMessage())
                        .message(personNotFoundExeption.getClass().getName())
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

