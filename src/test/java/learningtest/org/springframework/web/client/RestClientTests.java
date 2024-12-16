package learningtest.org.springframework.web.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

/**
 * Tests for {@link RestClient}.
 *
 * @author Johnny Lim
 */
class RestClientTests {

    @Test
    void body() {
        RestClient restClient = RestClient.create();
        String body = restClient.get().uri("https://example.com").retrieve().body(String.class);
        System.out.println(body);
    }

    @Test
    void toEntity() {
        RestClient restClient = RestClient.create();
        ResponseEntity<String> entity = restClient.get().uri("https://example.com").retrieve().toEntity(String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getHeaders());
        System.out.println(entity.getBody());
    }

}
