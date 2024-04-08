package com.izeye.helloworld.springboot;

import jakarta.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * {@link RestController} for {@link Person}.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/persons")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @GetMapping
    public List<Person> getPersons() {
        return List.of(new Person("Johnny", "Lim"));
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person, ServletRequest request) throws IOException {
        log.info("request.getInputStream(): {}", StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("((ContentCachingRequestWrapper) request).getContentAsString(): {}", ((ContentCachingRequestWrapper) request).getContentAsString());
        return person;
    }

}
