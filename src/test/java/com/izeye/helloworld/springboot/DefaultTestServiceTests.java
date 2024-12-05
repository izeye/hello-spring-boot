package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link DefaultTestService}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class DefaultTestServiceTests {

    @Autowired
    DefaultTestService service;

    @Test
    void test() {
    }

}
