package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RestTemplate}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class RestTemplateTests {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    Jackson2ObjectMapperBuilder objectMapperBuilder;

    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Test
    void test() {
        assertThat(this.restTemplate.getRequestFactory()).isInstanceOf(SimpleClientHttpRequestFactory.class);

        String url = "https://www.google.com/";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");

        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();

        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);

        HttpHeaders headers = responseEntity.getHeaders();
        assertThat(headers).containsEntry("Content-Encoding", List.of("gzip"));
        System.out.println(headers);
        // Garbled text is expected as content-decoding didn't happen.
        System.out.println(responseEntity.getBody());
    }

    @Test
    void exchange() {
        List<Person> persons = this.restTemplate.exchange("http://localhost:8080/persons", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }).getBody();

        assertThat(persons).singleElement().satisfies((person) -> assertThat(person.firstName()).isEqualTo("Johnny"));
    }

    @Test
    void exchangeWithSnakeCase() {
        RestTemplate camelCaseRestTemplate = changeToCamelCase(this.restTemplateBuilder, this.objectMapperBuilder).build();

        List<Person> persons = camelCaseRestTemplate.exchange("http://localhost:8080/persons/snake-case", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }).getBody();

        assertThat(persons).singleElement().satisfies((person) -> assertThat(person.firstName()).isEqualTo("Johnny"));
    }

    private static RestTemplateBuilder changeToCamelCase(RestTemplateBuilder restTemplateBuilder, Jackson2ObjectMapperBuilder objectMapperBuilder) {
        return restTemplateBuilder.messageConverters(List.of(new MappingJackson2HttpMessageConverter(
                objectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).build())));
    }

}
