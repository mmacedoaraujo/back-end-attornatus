package com.example.mmacedoaraujo.registrationapi.repository;

import com.example.mmacedoaraujo.registrationapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
