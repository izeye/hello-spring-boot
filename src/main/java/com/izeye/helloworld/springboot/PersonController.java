package com.izeye.helloworld.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return new Person(id, "Johnny", "Lim");
    }

    @GetMapping
    public List<Person> getPersons() {
        return List.of(new Person(1L, "Johnny", "Lim"));
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return person;
    }

}
