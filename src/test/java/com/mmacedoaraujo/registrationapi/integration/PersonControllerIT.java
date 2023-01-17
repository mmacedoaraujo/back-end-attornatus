package com.mmacedoaraujo.registrationapi.integration;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.repository.PersonRepository;
import com.mmacedoaraujo.registrationapi.util.EntityCreator;
import com.mmacedoaraujo.registrationapi.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplate")
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PersonRepository personRepository;

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplate")
        public TestRestTemplate testRestTemplateCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port);

            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    void listAll() {
        Person savedPerson = personRepository.save(EntityCreator.createPerson());

        PageableResponse<Person> personPage = testRestTemplate.exchange("/persons", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Person>>() {
                }).getBody();

        Assertions.assertThat(personPage)
                .isNotNull();

        Assertions.assertThat(personPage.toList())
                .isNotNull()
                .hasSize(2);

        Assertions.assertThat(personPage.toList().get(0).getName())
                .isEqualTo(savedPerson.getName());

        Assertions.assertThat(personPage.toList().get(0).getId())
                .isEqualTo(savedPerson.getId());
    }

    @Test
    void findPersonById() {
        Person savedPerson = personRepository.save(EntityCreator.createPerson());

        Person foundPerson = testRestTemplate.getForObject("/persons/{id}", Person.class, savedPerson.getId());


        Assertions.assertThat(foundPerson)
                .isNotNull();

        Assertions.assertThat(foundPerson.getId())
                .isNotNull()
                .isEqualTo(savedPerson.getId());
    }

    @Test
    void findByName() {
        Person savedPerson = personRepository.save(EntityCreator.createPerson());

        Long expectedId = savedPerson.getId();

        String expectedName = savedPerson.getName();

        String url = String.format("/persons/findByName?name=%s", expectedName);

        PageableResponse<Person> personPage = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Person>>() {
                }).getBody();

        Assertions.assertThat(personPage)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(personPage.toList().get(0).getId())
                .isEqualTo(expectedId);

        Assertions.assertThat(personPage.toList().get(0).getName())
                .isEqualTo(expectedName);
    }

    @Test
    @Disabled
    void mainAddress() {
        Person savedPerson = personRepository.save(EntityCreator.createPerson());

        Address foundAddress = testRestTemplate.getForObject("/persons/getMainAddress/{id}", Address.class, savedPerson.getId());

        Assertions.assertThat(foundAddress)
                .isNotNull();
    }

    @Test
    void updateUser() {
        Person savedPerson = personRepository.save(EntityCreator.createPerson());

        savedPerson.setName("new name");

        ResponseEntity<Void> personResponseEntity = testRestTemplate.exchange("/persons/update",
                HttpMethod.PUT,
                new HttpEntity<>(EntityCreator.createPersonPutRequestBody()),
                Void.class);

        Assertions.assertThat(personResponseEntity).isNotNull();
        Assertions.assertThat(personResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deletePersonAddress() {
        Person savedPerson = personRepository.save(EntityCreator.createPerson());

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/persons/deletePersonAddress/{personId}/{addressId}",
                HttpMethod.DELETE,
                null,
                Void.class,
                1L, 1L);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
