package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Environment}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class EnvironmentTests {

    @Autowired
    Environment environment;

    @Test
    void activeProfilesIsEmpty() {
        assertThat(this.environment.getActiveProfiles()).isEmpty();
    }

}
