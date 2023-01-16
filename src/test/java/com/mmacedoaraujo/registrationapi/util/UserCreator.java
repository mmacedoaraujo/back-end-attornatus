package com.mmacedoaraujo.registrationapi.util;

import com.mmacedoaraujo.registrationapi.domain.Person;

import java.time.LocalDate;

public class UserCreator {

    public static Person createUser() {
        return Person.builder()
                .id(1L)
                .name("Teste")
                .birthdate(LocalDate.now())
                .addressList(null)
                .build();
    }
}
