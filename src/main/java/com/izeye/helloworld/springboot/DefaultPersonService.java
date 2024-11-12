package com.izeye.helloworld.springboot;

import org.springframework.stereotype.Service;

/**
 * Default {@link PersonService}.
 *
 * @author Johnny Lim
 */
@Service
public class DefaultPersonService implements PersonService {

    @Override
    public Person getPerson(String username) {
        return new Person(username, "Johnny", "Lim");
    }

}
