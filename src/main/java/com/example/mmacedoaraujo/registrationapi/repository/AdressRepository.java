package com.example.mmacedoaraujo.registrationapi.repository;

import com.example.mmacedoaraujo.registrationapi.domain.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Long> {
}
