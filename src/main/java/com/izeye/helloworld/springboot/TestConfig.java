package com.izeye.helloworld.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Configuration} for testing.
 *
 * @author Johnny Lim
 */
@Configuration
public class TestConfig {

    @Bean(destroyMethod = "destroy")
    public DestroyHangComponent destroyHangComponent() {
        return new DestroyHangComponent();
    }

}
