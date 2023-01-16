package com.mmacedoaraujo.registrationapi.exceptions;

public class PersonNotFoundExeption extends RuntimeException {

    public PersonNotFoundExeption(String message) {
        super(message);
    }
}
