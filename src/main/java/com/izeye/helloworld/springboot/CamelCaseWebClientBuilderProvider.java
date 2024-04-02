package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Provider for {@link WebClient.Builder} using camelCase.
 *
 * @author Johnny Lim
 */
@Component
public class CamelCaseWebClientBuilderProvider {

    private final WebClient.Builder builder;

    public CamelCaseWebClientBuilderProvider(WebClient.Builder builder, ObjectMapper mapper) {
        ObjectMapper camelCaseObjectMapper = mapper.copy()
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        this.builder = builder.codecs((configurer) -> {
            ClientCodecConfigurer.ClientDefaultCodecs codecs = configurer.defaultCodecs();
            codecs.jackson2JsonEncoder(new Jackson2JsonEncoder(camelCaseObjectMapper));
            codecs.jackson2JsonDecoder(new Jackson2JsonDecoder(camelCaseObjectMapper));
        });
    }

    public WebClient.Builder provide() {
        return builder.clone();
    }

}
