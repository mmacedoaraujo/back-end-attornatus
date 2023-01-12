package com.example.mmacedoaraujo.registrationapi.repository;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
