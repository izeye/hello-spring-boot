package learningtest.org.springframework.web.util;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UriComponentsBuilder}.
 *
 * @author Johnny Lim
 */
class UriComponentsBuilderTests {

    @Test
    void test() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://httpbin.org/headers")
                .queryParam("a", "test")
                .queryParam("b", "테스트")
                .build()
                .encode()
                .toUri();
        assertThat(uri).hasToString("https://httpbin.org/headers?a=test&b=%ED%85%8C%EC%8A%A4%ED%8A%B8");
    }

}
