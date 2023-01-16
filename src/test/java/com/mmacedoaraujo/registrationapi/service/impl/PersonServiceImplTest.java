package com.mmacedoaraujo.registrationapi.service.impl;

import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.exceptions.UserNotFoundExeption;
import com.mmacedoaraujo.registrationapi.repository.PersonRepository;
import com.mmacedoaraujo.registrationapi.util.UserCreator;
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

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        PageImpl<Person> personPage = new PageImpl<>(List.of(UserCreator.createUser(), UserCreator.createUser()));
        BDDMockito.when(personRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(personPage);

        BDDMockito.when(personRepository.findAll()).thenReturn(List.of(UserCreator.createUser(), UserCreator.createUser(), UserCreator.createUser()));

        BDDMockito.when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(UserCreator.createUser()));
    }

    @Test
    @DisplayName("listAll return a page of Person when successfull")
    void listAll_ReturnsListOfPersonInsidePageObject_WhenSuccessful() {
        Person validPerson = UserCreator.createUser();
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
    @DisplayName("findUserById return a valid Person when successfull")
    void findPersonById_ReturnsAValidUser_WhenSuccessful() {
        Person validPerson = UserCreator.createUser();
        Person foundPerson = personService.findPersonById(1L);

        Assertions.assertThat(foundPerson)
                .isNotNull()
                .isInstanceOf(Person.class);

        Assertions.assertThat(foundPerson.getId())
                .isNotNull()
                .isEqualTo(validPerson.getId());
    }

    @Test
    @DisplayName("findUserById throws UserNotFoundException when user id does not exist")
    void findUserById_ThrowsException_WhenUserDoesNotExist() {
        BDDMockito.when(personRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(UserNotFoundExeption.class)
                .isThrownBy(() -> this.personService.findPersonById(ArgumentMatchers.anyLong()));
    }
}