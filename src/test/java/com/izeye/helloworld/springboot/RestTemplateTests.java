package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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

}
