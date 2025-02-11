package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Tests for {@link PersonController} with {@link RestTemplate}.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerRestTemplateTests {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    int port;

    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        this.restTemplate = this.restTemplateBuilder.build();
    }

    @Test
    void getPersonForTestingTomcatMaxKeepAliveRequests() {
        for (int i = 0; i < 10; i++) {
            String response = this.restTemplate.getForObject(
                    "http://localhost:{port}/persons/{id}", String.class, this.port, i);
            System.out.println(response);
        }
    }

    @Test
    void getPersonForTestingTomcatKeepAliveTimeout() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            String response = this.restTemplate.getForObject(
                    "http://localhost:{port}/persons/{id}", String.class, this.port, i);
            System.out.println(response);
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
