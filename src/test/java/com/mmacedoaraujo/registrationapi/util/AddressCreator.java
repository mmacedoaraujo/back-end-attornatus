package com.mmacedoaraujo.registrationapi.util;

import com.mmacedoaraujo.registrationapi.domain.Address;

public class AddressCreator {

    public static Address createAddress() {
        return Address.builder()
                .id(1L)
                .cep("cep")
                .cidade("cidade")
                .numero(00)
                .logradouro("logradouro")
                .personId(PersonCreator.createPerson())
                .enderecoPrincipal(true)
                .build();
    }
}
