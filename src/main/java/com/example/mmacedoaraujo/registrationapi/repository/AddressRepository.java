package com.example.mmacedoaraujo.registrationapi.repository;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT addr FROM Address addr WHERE addr.enderecoPrincipal = true")
    List<Address> listOnlyMainAddress();
}
