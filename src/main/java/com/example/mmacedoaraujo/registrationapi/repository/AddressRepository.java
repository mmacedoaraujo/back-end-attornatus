package com.example.mmacedoaraujo.registrationapi.repository;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
