package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PersonController}.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class PersonControllerTests {

    @Autowired
    private WebTestClient client;

    @Test
    void addPerson() {
        Person person = new Person("Johnny", "Lim");
        Person returnValue = this.client.post()
                .uri("/persons")
                .bodyValue(person)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Person.class)
                .getResponseBody()
                .single()
                .block();
        assertThat(person).isEqualTo(returnValue);
    }
}
