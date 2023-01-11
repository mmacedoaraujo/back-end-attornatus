package com.example.mmacedoaraujo.registrationapi;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.repository.AddressRepository;
import com.example.mmacedoaraujo.registrationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class RegistrationApiApplication {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(RegistrationApiApplication.class, args);
    }


    @Bean
    public void run() {
        User user = new User(null, "Marcos", LocalDate.now(), null);
        User user2 = new User(null, "Alessandra", LocalDate.now(), null);
        Address address = new Address(null, "Teste", "27638762", 19, "Teste", false, user);
        Address address2 = new Address(null, "Teste2", "2762323", 123, "Teste", false, user);
        Address address3 = new Address(null, "Teste2", "2762323", 123, "Teste", false, user2);
        userRepository.saveAll(Arrays.asList(user, user2));
        addressRepository.saveAll(Arrays.asList(address2, address, address3));

    }
}
