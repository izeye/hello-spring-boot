package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PersonService}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class PersonServiceTests {

    @Autowired
    private PersonService personService;

    @Test
    void getPerson() {
        assertThat(this.personService.getPerson(1L)).isSameAs(this.personService.getPerson(1L));
    }

}
