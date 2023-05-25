package com.izeye.helloworld.springboot;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    private final WebClient webClient;

    public TestController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/httpServletRequest")
    public String http(HttpServletRequest request) {
        return "name='%s'".formatted(request.getParameter("name"));
    }

    @GetMapping("/webClient")
    public String webClient() {
        return this.webClient.get().uri("https://www.google.com/").retrieve().toEntity(String.class).block().getBody();
    }

}
