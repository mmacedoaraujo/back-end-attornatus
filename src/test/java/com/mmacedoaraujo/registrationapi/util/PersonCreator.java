package com.mmacedoaraujo.registrationapi.util;

import com.mmacedoaraujo.registrationapi.domain.Person;

import java.time.LocalDate;

public class PersonCreator {

    public static Person createPerson() {
        return Person.builder()
                .id(1L)
                .name("Teste")
                .birthdate(LocalDate.now())
                .addressList(null)
                .build();
    }
}
