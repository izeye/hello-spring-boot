package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * Tests for {@link WebClient}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class WebClientTests {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        this.webClient = webClientBuilder.build();
    }

    @Test
    void test() {
        Person person = new Person("Johnnhy", "Lim");
        Map<String, Object> response = this.webClient.post()
                .uri("https://httpbin.org/post")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        System.out.println(response);
    }

}
