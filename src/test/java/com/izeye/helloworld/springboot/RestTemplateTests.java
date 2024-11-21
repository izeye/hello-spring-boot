package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.netty.handler.logging.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RestTemplate}.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTemplateTests {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    Jackson2ObjectMapperBuilder objectMapperBuilder;

    @LocalServerPort
    int port;

    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        HttpClient httpClient = HttpClient.create()
                .compress(true)
                .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        this.restTemplate = restTemplateBuilder.requestFactory(() -> new ReactorClientHttpRequestFactory(httpClient)).build();
    }

    @Test
    void testGzip() {
        assertThat(this.restTemplate.getRequestFactory()).isInstanceOf(ReactorClientHttpRequestFactory.class);

        String url = "https://www.google.com/";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");

        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();

        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);

        HttpHeaders headers = responseEntity.getHeaders();
        assertThat(headers).doesNotContainEntry("Content-Encoding", List.of("gzip"));
        System.out.println(headers);
        System.out.println(responseEntity.getBody());
    }

    @Test
    void testZstd() {
        assertThat(this.restTemplate.getRequestFactory()).isInstanceOf(ReactorClientHttpRequestFactory.class);

        String url = "https://www.facebook.com/";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "zstd");

        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();

        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);

        HttpHeaders headers = responseEntity.getHeaders();
        assertThat(headers).doesNotContainEntry("Content-Encoding", List.of("zstd"));
        System.out.println(headers);
        System.out.println(responseEntity.getBody());
    }

    @Test
    void exchange() {
        String url = String.format("http://localhost:%s/persons", this.port);

        List<Person> persons = this.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }).getBody();

        assertThat(persons).singleElement().satisfies((person) -> assertThat(person.firstName()).isEqualTo("Johnny"));
    }

    @Test
    void exchangeWithSnakeCase() {
        RestTemplate camelCaseRestTemplate = changeToCamelCase(this.restTemplateBuilder, this.objectMapperBuilder).build();

        String url = String.format("http://localhost:%s/persons/snake-case", this.port);

        List<Person> persons = camelCaseRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }).getBody();

        assertThat(persons).singleElement().satisfies((person) -> assertThat(person.firstName()).isEqualTo("Johnny"));
    }

    private static RestTemplateBuilder changeToCamelCase(RestTemplateBuilder restTemplateBuilder, Jackson2ObjectMapperBuilder objectMapperBuilder) {
        return restTemplateBuilder.messageConverters(List.of(new MappingJackson2HttpMessageConverter(
                objectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).build())));
    }

}