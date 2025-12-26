package com.izeye.helloworld.springboot;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping(path = "/path-variable/{id}")
    public String getPathVariable(@PathVariable String id) {
        return id;
    }

    // Downloading in a web browser.
    @GetMapping(path = "/yaml", produces = MediaType.APPLICATION_YAML_VALUE)
    public String getYaml() {
        return """
                name: 테스트
                """;
    }

    // Note that it's okay as "text/yaml" is being changed to "text/yaml;charset=UTF-8".
    // See https://www.rfc-editor.org/rfc/rfc9512.html for deprecation.
    @GetMapping(path = "/yaml-deprecated-produces", produces = "text/yaml")
    public String getYamlDeprecatedProduces() {
        return """
                name: 테스트
                """;
    }

    // No download but broken encoding.
    @GetMapping(path = "/yaml-deprecated")
    public String getYamlDeprecated(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/yaml");
        return """
                name: 테스트
                """;
    }

}
