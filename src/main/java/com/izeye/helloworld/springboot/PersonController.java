package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * {@link RestController} for {@link Person}.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/persons")
public class PersonController {

    private final ObjectMapper objectMapper;

    public PersonController(Jackson2ObjectMapperBuilder objectMapperBuilder) {
        this.objectMapper = objectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).build();
    }

    @GetMapping
    public List<Person> getPersons() {
        return List.of(new Person("Johnny", "Lim"));
    }

    @GetMapping(path = "/snake-case", produces = "application/json")
    public String getPersonsAsSnakeCase() throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(List.of(new Person("Johnny", "Lim")));
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return person;
    }

}
