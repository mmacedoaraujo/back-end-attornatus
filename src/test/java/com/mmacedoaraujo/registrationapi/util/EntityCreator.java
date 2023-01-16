package com.mmacedoaraujo.registrationapi.util;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.requests.PersonAddressPostRequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntityCreator {

    public static PersonAddressPostRequestBody createPersonAddressPostRequestBody() {

        return PersonAddressPostRequestBody.builder()
                .cep("cep")
                .cidade("cidade")
                .numero(18)
                .name("nome")
                .personId(EntityCreator.createPerson())
                .enderecoPrincipal(true)
                .logradouro("logradouro")
                .birthdate(LocalDate.now())
                .build();
    }

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

    public static Person createPerson() {
        return Person.builder()
                .id(1L)
                .name("Teste")
                .birthdate(LocalDate.now())
                .addressList(List.of(EntityCreator.createAddress(), EntityCreator.createAddress(), EntityCreator.createAddress()))
                .build();
    }
}
