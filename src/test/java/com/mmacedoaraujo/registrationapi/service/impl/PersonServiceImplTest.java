package com.mmacedoaraujo.registrationapi.service.impl;

import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.exceptions.PersonNotFoundExeption;
import com.mmacedoaraujo.registrationapi.repository.PersonRepository;
import com.mmacedoaraujo.registrationapi.util.AddressCreator;
import com.mmacedoaraujo.registrationapi.util.PersonCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressServiceImpl addressService;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        PageImpl<Person> personPage = new PageImpl<>(List.of(PersonCreator.createPerson(), PersonCreator.createPerson()));
        BDDMockito.when(personRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(personPage);

        BDDMockito.when(personRepository.findPersonByName(ArgumentMatchers.any(PageRequest.class), ArgumentMatchers.anyString())).thenReturn(personPage);

        BDDMockito.when(personRepository.findAll()).thenReturn(List.of(PersonCreator.createPerson(), PersonCreator.createPerson(), PersonCreator.createPerson()));

        BDDMockito.when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(PersonCreator.createPerson()));
    }

    @Test
    @DisplayName("listAll return a page of Person when successfull")
    void listAll() {
        Person validPerson = PersonCreator.createPerson();
        Page<Person> usersPage = personService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(personService.listAll(PageRequest.of(1, 1))).getClass().equals(Page.class);

        Assertions.assertThat(usersPage)
                .isNotNull();

        Assertions.assertThat(usersPage.toList())
                .isNotNull()
                .hasSize(2);

        Assertions.assertThat(usersPage.toList().get(0).getName())
                .isEqualTo(validPerson.getName());

        Assertions.assertThat(usersPage.toList().get(0).getId())
                .isEqualTo(validPerson.getId());


    }

    @Test
    @DisplayName("findPersonById return a valid Person when successfull")
    void findPersonById() {
        Person validPerson = PersonCreator.createPerson();
        Person foundPerson = personService.findPersonById(1L);

        Assertions.assertThat(foundPerson)
                .isNotNull()
                .isInstanceOf(Person.class);

        Assertions.assertThat(foundPerson.getId())
                .isNotNull()
                .isEqualTo(validPerson.getId());
    }

    @Test
    @DisplayName("findPersonById throws UserNotFoundException when user id does not exist")
    void findPersonById_ExceptionTest() {
        BDDMockito.when(personRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(PersonNotFoundExeption.class)
                .isThrownBy(() -> this.personService.findPersonById(ArgumentMatchers.anyLong()));
    }

    @Test
    @DisplayName("findePersonByName returns a valid Person when successfull")
    void findPersonByName() {
        Person validPerson = PersonCreator.createPerson();

        Page<Person> personFound = personService.findPersonByName(PageRequest.of(1, 1), "teste");

        Assertions.assertThat(personFound)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(personFound.toList().get(0).getId())
                .isEqualTo(validPerson.getId());

        Assertions.assertThat(personFound.toList().get(0).getName())
                .isEqualTo(validPerson.getName());
    }

    @Test
    @DisplayName("updatePerson changes information on entity when successful")
    void updatePerson() {
        Person test = new Person(null, "test", LocalDate.now(), null);

        Assertions.assertThatCode(() -> this.personService.updatePerson(test)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("setMainAddress returns a person when successful")
    void setMainAddress() {
        Person validPerson = PersonCreator.createPerson();

        Assertions.assertThat(personService.setMainAddress(1L, 1L))
                .isNotNull()
                .isInstanceOf(Person.class)
                .isEqualTo(validPerson);


    }

    @Test
    @DisplayName("listAllPersonAddress returns a list of address when successful")
    void listAllPersonAddresses() {
        Assertions.assertThat(this.personService.listAllPersonAddresses(1L))
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Assertions.assertThatCode(() -> this.personService.listAllPersonAddresses(1L)).doesNotThrowAnyException();
    }


}