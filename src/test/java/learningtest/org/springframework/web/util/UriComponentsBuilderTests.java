package learningtest.org.springframework.web.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link UriComponentsBuilder}.
 *
 * @author Johnny Lim
 */
class UriComponentsBuilderTests {

    @Test
    void test() {
        URI uri = UriComponentsBuilder.fromUriString("https://httpbin.org/headers")
                .queryParam("a", "test")
                .queryParam("b", "테스트")
                .queryParam("empty", "")
                .queryParam("null", (String) null)
                .build()
                .encode()
                .toUri();
        assertThat(uri).hasToString("https://httpbin.org/headers?a=test&b=%ED%85%8C%EC%8A%A4%ED%8A%B8&empty=&null");
    }

    @Test
    void buildAndEncodeWithUriTemplateLikeValue() {
        URI uri = UriComponentsBuilder.fromUriString("https://httpbin.org/headers")
                .queryParam("a", "a{b}c")
                .build()
                .encode() // UriComponents.encode()
                .toUri();
        assertThat(uri).hasToString("https://httpbin.org/headers?a=a%7Bb%7Dc");
    }

    @Test
    void encodeAndBuildWithUriTemplateLikeValue() {
        assertThatIllegalStateException()
                .isThrownBy(() -> UriComponentsBuilder.fromUriString("https://httpbin.org/headers")
                        .queryParam("a", "a{b}c")
                        .encode() // UriComponentsBuilder.encode()
                        .build()
                        .toUri());
    }

    @Test
    void getQueryParams() {
        String url = "https://httpbin.org/headers?a=test&b=%ED%85%8C%EC%8A%A4%ED%8A%B8";
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUriString(url).build().getQueryParams();
        assertThat(queryParams.get("a")).containsExactly("test");
        assertThat(queryParams.get("b")).containsExactly("%ED%85%8C%EC%8A%A4%ED%8A%B8");
    }

}
