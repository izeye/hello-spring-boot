package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.netty.handler.logging.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebClientTests {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    Jackson2ObjectMapperBuilder objectMapperBuilder;

    @LocalServerPort
    int port;

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

    @Test
    void testZstd() {
        String uri = "https://www.facebook.com/";
        ResponseEntity<String> responseEntity = this.webClient.get()
                .uri(uri).header(HttpHeaders.ACCEPT_ENCODING, "zstd")
                .retrieve()
                .toEntity(String.class).block();
        HttpHeaders headers = responseEntity.getHeaders();
        // Reactor Netty has already handled content-encoding under the hood, so there's no Content-Encoding header.
        assertThat(headers).doesNotContainEntry("Content-Encoding", List.of("zstd"));
        System.out.println(headers);
        System.out.println(responseEntity.getBody());
    }

    @Test
    void exchange() {
        String url = String.format("http://localhost:%s/persons", this.port);

        List<Person> persons = this.webClient.get().uri(url).retrieve().bodyToMono(new ParameterizedTypeReference<List<Person>>() {
        }).block();

        assertThat(persons).singleElement().satisfies((person) -> assertThat(person.firstName()).isEqualTo("Johnny"));
    }

    @Test
    void exchangeWithSnakeCase() {
        WebClient webClient = configureToUseSnakeCase(this.webClientBuilder, this.objectMapperBuilder).build();

        String url = String.format("http://localhost:%s/persons/snake-case", this.port);

        List<Person> persons = webClient.get().uri(url).retrieve().bodyToMono(new ParameterizedTypeReference<List<Person>>() {
        }).block();

        assertThat(persons).singleElement().satisfies((person) -> assertThat(person.firstName()).isEqualTo("Johnny"));
    }

    private static WebClient.Builder configureToUseSnakeCase(WebClient.Builder webClientBuilder, Jackson2ObjectMapperBuilder objectMapperBuilder) {
        ObjectMapper objectMapper = objectMapperBuilder
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .build();

        return webClientBuilder.codecs(configurer -> {
            configurer.defaultCodecs()
                    .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
            configurer.defaultCodecs()
                    .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
        });
    }

}
