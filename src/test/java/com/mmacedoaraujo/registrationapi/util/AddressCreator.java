package com.mmacedoaraujo.registrationapi.util;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;

import java.time.LocalDate;

public class AddressCreator {

    public static Address createAddress() {
        return Address.builder()
                .id(1L)
                .cep("cep")
                .cidade("cidade")
                .numero(00)
                .logradouro("logradouro")
                .personId(new Person(1L, "Teste", LocalDate.now(), null))
                .enderecoPrincipal(true)
                .build();
    }
}
