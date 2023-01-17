package com.mmacedoaraujo.registrationapi.controller;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.requests.PersonAddressPostRequestBody;
import com.mmacedoaraujo.registrationapi.service.impl.PersonServiceImpl;
import com.mmacedoaraujo.registrationapi.util.EntityCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;
    @Mock
    private PersonServiceImpl personService;


    @BeforeEach
    void setUp() {
        Person person = EntityCreator.createPerson();
        person.setId(1L);

        PageImpl<Person> personPage = new PageImpl<>(List.of(EntityCreator.createPerson()));

        BDDMockito.when(personService.listAll(ArgumentMatchers.any())).thenReturn(personPage);

        BDDMockito.when(personService.findPersonById(anyLong())).thenReturn(person);

        BDDMockito.when(personService.findPersonByName(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(personPage);

        BDDMockito.when(personService.getPersonMainAddress(anyLong())).thenReturn(EntityCreator.createAddress());

        BDDMockito.when(personService.listAllPersonAddresses(anyLong())).thenReturn(List.of(EntityCreator.createAddress()));

        BDDMockito.when(personService.savePerson(ArgumentMatchers.any(PersonAddressPostRequestBody.class))).thenReturn(EntityCreator.createPerson());

        BDDMockito.when(personService.saveNewAddress(ArgumentMatchers.any(Address.class), anyLong())).thenReturn(EntityCreator.createPerson());

        BDDMockito.when(personService.setMainAddress(anyLong(), anyLong())).thenReturn(EntityCreator.createPerson());
    }

    @Test
    @Disabled
    void listAll() {
        Person person = EntityCreator.createPerson();
        Page<Person> personPage = personController.listAll(null).getBody();

        Assertions.assertThat(personPage)
                .isNotNull();

        Assertions.assertThat(personPage)
                .isNotNull()
                .isInstanceOf(Page.class);

        Assertions.assertThat(personPage.toList().get(0).getName())
                .isEqualTo(person.getName());

        Assertions.assertThat(personPage.toList().get(0).getId())
                .isEqualTo(person.getId());

    }

    @Test
    void findById() {
        Person person = EntityCreator.createPerson();
        person.setId(1L);

        Person foundPerson = personController.findPersonById(2L).getBody();

        Assertions.assertThat(foundPerson)
                .isNotNull()
                .isInstanceOf(Person.class);

        Assertions.assertThat(foundPerson.getId())
                .isEqualTo(person.getId());
    }

    @Test
    void findByName() {
        Person person = EntityCreator.createPerson();

        Page<Person> personFoundByName = personController.findByName(PageRequest.of(1, 1), person.getName()).getBody();

        Assertions.assertThat(personFoundByName)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .isInstanceOf(Page.class);

        Assertions.assertThat(personFoundByName.toList().get(0))
                .isNotNull()
                .isEqualTo(person)
                .isInstanceOf(Person.class);
    }

    @Test
    void mainAddress() {
        Address address = EntityCreator.createAddress();

        Assertions.assertThat(personController.mainAddress(1L).getBody())
                .isInstanceOf(Address.class)
                .isNotNull()
                .isEqualTo(address);

        Assertions.assertThatCode(() -> this.personController.mainAddress(1L)).doesNotThrowAnyException();
    }

    @Test
    void allAddresses() {
        Address comparisonAddress = EntityCreator.createAddress();

        List<Address> personList = personController.allAddresses(1L).getBody();

        Assertions.assertThat(personList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(personList.get(0).getNumero())
                .isEqualTo(comparisonAddress.getNumero());

        Assertions.assertThat(personList.get(0).getCidade())
                .isEqualTo(comparisonAddress.getCidade());

        Assertions.assertThat(personList.get(0).getPersonId())
                .isEqualTo(comparisonAddress.getPersonId());
    }

    @Test
    void saveNewPerson() {
        Person comparisonPerson = EntityCreator.createPerson();

        Person personFromController = personController.saveNewPerson(EntityCreator.createPersonAddressPostRequestBody()).getBody();

        Assertions.assertThat(personFromController)
                .isNotNull()
                .isInstanceOf(Person.class)
                .isEqualTo(comparisonPerson);

        Assertions.assertThat(personFromController.getId())
                .isEqualTo(comparisonPerson.getId());

        Assertions.assertThat(personFromController.getName())
                .isEqualTo(comparisonPerson.getName());

        Assertions.assertThat(personFromController.getBirthdate())
                .isEqualTo(comparisonPerson.getBirthdate());

        Assertions.assertThat(personFromController.getAddressList())
                .isEqualTo(comparisonPerson.getAddressList());
    }

    @Test
    void addNewAddress() {
        Person comparisonPerson = EntityCreator.createPerson();

        Person personFromController = personController.addNewAddress(EntityCreator.createAddress(), 1L).getBody();

        Assertions.assertThat(personFromController)
                .isNotNull()
                .isInstanceOf(Person.class)
                .isEqualTo(comparisonPerson);

        Assertions.assertThat(personFromController.getId())
                .isEqualTo(comparisonPerson.getId());

        Assertions.assertThat(personFromController.getName())
                .isEqualTo(comparisonPerson.getName());

        Assertions.assertThat(personFromController.getBirthdate())
                .isEqualTo(comparisonPerson.getBirthdate());

        Assertions.assertThat(personFromController.getAddressList())
                .isEqualTo(comparisonPerson.getAddressList());
    }

    @Test
    void deletePersonAddress() {

        Assertions.assertThatCode(() -> personController.deletePersonAddress(1L, 1L)).doesNotThrowAnyException();
    }

    @Test
    void updatePerson() {

        Assertions.assertThatCode(() -> personController.updateUser(EntityCreator.createPersonPutRequestBody())).doesNotThrowAnyException();
    }

    @Test
    void setMainAddress() {
        Person comparisonPerson = EntityCreator.createPerson();

        Person personFromController = personController.setMainAddress(1L, 1L).getBody();

        Assertions.assertThat(personFromController)
                .isNotNull()
                .isInstanceOf(Person.class)
                .isEqualTo(comparisonPerson);

        Assertions.assertThat(personFromController.getId())
                .isEqualTo(comparisonPerson.getId());

        Assertions.assertThat(personFromController.getName())
                .isEqualTo(comparisonPerson.getName());

        Assertions.assertThat(personFromController.getBirthdate())
                .isEqualTo(comparisonPerson.getBirthdate());

        Assertions.assertThat(personFromController.getAddressList())
                .isEqualTo(comparisonPerson.getAddressList());

    }

}
