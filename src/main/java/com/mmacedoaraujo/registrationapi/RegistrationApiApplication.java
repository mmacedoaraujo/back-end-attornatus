package com.mmacedoaraujo.registrationapi;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.repository.AddressRepository;
import com.mmacedoaraujo.registrationapi.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class RegistrationApiApplication {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(RegistrationApiApplication.class, args);
    }


    @Bean
    public void run() {
        Person person = new Person(null, "Marcos", LocalDate.now(), null);
        Person person2 = new Person(null, "Alessandra", LocalDate.now(), null);
        Address address = new Address(null, "Teste", "27638762", 19, "Teste", true, person);
        Address address2 = new Address(null, "Teste2", "2762323", 123, "Teste", false, person);
        Address address3 = new Address(null, "Teste2", "2762323", 123, "Teste", true, person2);
        Address address4 = new Address(null, "Teste2", "2762323", 123, "Teste", true, person2);
        personRepository.saveAll(Arrays.asList(person, person2));
        addressRepository.saveAll(Arrays.asList(address2, address, address3, address4));

    }
}
