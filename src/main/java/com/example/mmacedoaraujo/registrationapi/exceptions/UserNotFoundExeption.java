package com.example.mmacedoaraujo.registrationapi.exceptions;

public class UserNotFoundExeption extends RuntimeException {

    public UserNotFoundExeption(String message) {
        super(message);
    }
}
