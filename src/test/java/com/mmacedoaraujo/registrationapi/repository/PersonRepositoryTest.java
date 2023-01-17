package com.mmacedoaraujo.registrationapi.repository;

import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.util.EntityCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for repository test")
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void savePerson() {
        Person person = EntityCreator.createPerson();
        Person savedPerson = this.personRepository.save(person);

        Assertions.assertThat(savedPerson).isNotNull();
        Assertions.assertThat(savedPerson.getId()).isNotNull();
        Assertions.assertThat(savedPerson.getName()).isNotNull().isEqualTo(person.getName());
    }

    @Test
    void saveUpdatesPerson() {
        Person personToBeUpdated = EntityCreator.createPerson();

        Person savedPerson = this.personRepository.save(personToBeUpdated);

        savedPerson.setName("Repository test");

        Person updatedPerson = this.personRepository.save(savedPerson);

        Assertions.assertThat(updatedPerson).isNotNull();
        Assertions.assertThat(updatedPerson.getId()).isNotNull();
        Assertions.assertThat(updatedPerson).isEqualTo(savedPerson);
    }

    @Test
    void delete() {
        Person person = EntityCreator.createPerson();

        Person savedPerson = this.personRepository.save(person);

        this.personRepository.delete(savedPerson);

        Optional<Person> personFoundById = this.personRepository.findById(savedPerson.getId());

        Assertions.assertThat(personFoundById).isEmpty();
    }

    @Test
    void findPersonByName() {
        Person person = EntityCreator.createPerson();

        Person savedPerson = this.personRepository.save(person);

        String personName = savedPerson.getName();
        Page<Person> personByName = this.personRepository.findPersonByName(PageRequest.of(1, 1), personName);

        Assertions.assertThat(personName)
                .isNotEmpty()
                .contains(personName);
    }

    @Test
    void findPersonByName_ReturnsEmptyPage() {
        Page<Person> randomTest = this.personRepository.findPersonByName(PageRequest.of(1, 1), "asdasdasdas");
        Assertions.assertThat(randomTest).isEmpty();
    }
}
