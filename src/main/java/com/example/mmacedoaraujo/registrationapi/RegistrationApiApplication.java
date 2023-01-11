package com.example.mmacedoaraujo.registrationapi;

import com.example.mmacedoaraujo.registrationapi.domain.Adress;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.repository.AdressRepository;
import com.example.mmacedoaraujo.registrationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@RequiredArgsConstructor
public class RegistrationApiApplication {

    private final UserRepository userRepository;
    private final AdressRepository adressRepository;

    public static void main(String[] args) {
        SpringApplication.run(RegistrationApiApplication.class, args);
    }


    @Bean
    public void run() {
        User user = new User(null, "Marcos", LocalDate.now(), null);
        Adress adress = new Adress(null, "Teste", "27638762", 19, "Teste", user);
        userRepository.save(user);
        adressRepository.save(adress);

    }
}
