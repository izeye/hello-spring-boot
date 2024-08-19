package com.izeye.helloworld.springboot;

import io.netty.handler.logging.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
        HttpClient httpClient = HttpClient.create()
                .compress(true)
                .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        this.webClient = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
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
    }

    @Test
    void testGzip() {
        ResponseEntity<String> responseEntity = this.webClient.get()
                .uri("https://www.google.com/").header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .retrieve()
                .toEntity(String.class).block();
        HttpHeaders headers = responseEntity.getHeaders();
        // Reactor Netty has already handled content-encoding under the hood, so there's no Content-Encoding header.
        assertThat(headers).doesNotContainEntry("Content-Encoding", List.of("gzip"));
        System.out.println(headers);
        System.out.println(responseEntity.getBody());
    }

}
