package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CamelCaseWebClientBuilderProvider}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class CamelCaseWebClientBuilderProviderTests {

    @Autowired
    private CamelCaseWebClientBuilderProvider provider;

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        this.webClient = provider.provide().build();
    }

    @Test
    void test() {
        Person person = new Person("Johnny", "Lim");
        Map<String, Object> response = this.webClient.post()
                .uri("https://httpbin.org/post")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        System.out.println(response);
        assertThat(response.get("data")).asString().contains("firstName");
    }

}