package com.izeye.helloworld.springboot;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
        ZstdContentCompressionExec zstdContentCompressionExec = new ZstdContentCompressionExec();
        HttpClient httpClient = HttpClientBuilder.create()
                .addExecInterceptorFirst("zstd", zstdContentCompressionExec)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        this.restTemplate = restTemplateBuilder.requestFactory(() -> requestFactory).build();
    }

    @Test
    void testGzip() {
        assertThat(this.restTemplate.getRequestFactory()).isInstanceOf(HttpComponentsClientHttpRequestFactory.class);

        String url = "https://www.google.com/";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");

        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();

        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);

        HttpHeaders headers = responseEntity.getHeaders();
        // Apache HttpClient has already handled content-encoding under the hood, so there's no Content-Encoding header.
        assertThat(headers).doesNotContainEntry("Content-Encoding", List.of("gzip"));
        System.out.println(headers);
        System.out.println(responseEntity.getBody());
    }

    @Test
    void testZstd() {
        String url = "https://www.facebook.com/";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "zstd");

        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();

        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);

        HttpHeaders headers = responseEntity.getHeaders();
        // Apache HttpClient has already handled content-encoding under the hood, so there's no Content-Encoding header.
        assertThat(headers).doesNotContainEntry("Content-Encoding", List.of("zstd"));
        System.out.println(headers);
        System.out.println(responseEntity.getBody());
    }

}
