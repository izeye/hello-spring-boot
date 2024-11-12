package com.izeye.helloworld.springboot;

import org.springframework.cache.annotation.Cacheable;

/**
 * Service interface for {@link Person}.
 *
 * @author Johnny Lim
 */
public interface PersonService {

    @Cacheable("persons")
    Person getPerson(String username);

}
