package com.izeye.helloworld.springboot;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public List<Person> getPersons() {
        return List.of(new Person("Johnny", "Lim"));
    }

}
