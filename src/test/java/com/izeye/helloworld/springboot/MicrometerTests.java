package com.izeye.helloworld.springboot;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Micrometer.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class MicrometerTests {

    @Autowired
    private MeterRegistry meterRegistry;

    @Test
    void counter() {
        Counter counter = this.meterRegistry.counter("my.counter");
        assertThat(counter.count()).isEqualTo(0);
        counter.increment();
        assertThat(counter.count()).isEqualTo(1);
    }

}
