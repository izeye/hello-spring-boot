package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests for {@link TestController}.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TestControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @SuppressWarnings("deprecation")
    void modelAndViewMissingModel() {
        this.webTestClient.get()
                .uri("/test/model-and-view/missing-model")
                .exchange()
                .expectStatus().isOk()
                // Due to MappingJackson2JsonView (AbstractJackson2View) setting jakarta.servlet.ServletResponse.setCharacterEncoding()
                // and how org.apache.coyote.Response.getContentType() works.
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(String.class).isEqualTo("{}");
    }

    @Test
    void responseBodyEmptyMap() {
        this.webTestClient.get()
                .uri("/test/response-body/empty-map")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("{}");
    }

}
