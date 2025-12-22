package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.netty.handler.logging.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
    void filter() {
        WebClient webClient = this.webClientBuilder.filter((request, next) -> {
            ClientRequest filtered = ClientRequest.from(request).header("foo", "bar").build();
            return next.exchange(filtered);
        }).build();

        Person person = new Person("Johnny", "Lim");
        Map<String, Object> response = webClient.post()
                .uri("https://httpbin.org/post")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        System.out.println(response);
    }

    @Test
    void filterForLoggingRequestAndResponse() {
        WebClient webClient = this.webClientBuilder.filter(loggingFilter()).build();

        Person person = new Person("Johnny", "Lim");
        Map<String, Object> response = webClient.post()
                .uri("https://httpbin.org/post")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        System.out.println("response: " + response);
    }

    private static ExchangeFilterFunction loggingFilter() {
        return (request, next) -> next.exchange(requestWithLogging(request)).map(responseWithLogging());
    }

    private static ClientRequest requestWithLogging(ClientRequest request) {
        return ClientRequest.from(request).body((outputMessage, context) -> request.body().insert(new ClientHttpRequestDecorator(outputMessage) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                return super.writeWith(Mono.from(body).doOnNext((dataBuffer) -> {
                    System.out.println("Request method: " + request.method());
                    System.out.println("Request URI: " + request.url());
                    System.out.println("Request headers: " + request.headers());
                    System.out.println("Request body: " + dataBuffer.toString(StandardCharsets.UTF_8));
                }));
            }
        }, context)).build();
    }

    private static Function<ClientResponse, ClientResponse> responseWithLogging() {
        return (response) -> response.mutate().body(data -> data.doOnNext((dataBuffer) -> {
            String body = dataBuffer.toString(StandardCharsets.UTF_8);
            System.out.println("Response body: " + body);
        })).build();
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

    @Test
    void uriTemplate() {
        String id = "테스트";
        String responseBody = this.webClient.get().uri("http://localhost:" + this.port + "/test/path-variable/{id}", id).retrieve().bodyToMono(String.class).block();
        assertThat(responseBody).isEqualTo(id);
    }

    @Test
    void uriTemplateWithEncodedUriVariable() {
        String id = "테스트";
        // uri() will encode URI variables including path ones, so you shouldn't encode them yourself.
        // Otherwise, they'll be encoded twice.
        String encodedId = URLEncoder.encode(id, StandardCharsets.UTF_8);
        String responseBody = this.webClient.get().uri("http://localhost:" + this.port + "/test/path-variable/{id}", encodedId).retrieve().bodyToMono(String.class).block();
        assertThat(responseBody).isNotEqualTo(id).isEqualTo(encodedId);
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
