package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TestProperties}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class TestPropertiesTests {

    @Autowired
    TestProperties properties;

    @Test
    void test() {
        assertThat(this.properties.getName()).isEqualTo("izeye");
        assertThat(this.properties.getValue()).isNull();
    }

}