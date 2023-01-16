package com.mmacedoaraujo.registrationapi.repository;

import com.mmacedoaraujo.registrationapi.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT person FROM Person person WHERE UPPER(person.name) LIKE ?1%")
    Page<Person> findPersonByName(Pageable pageable, String name);
}
