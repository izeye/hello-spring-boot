package com.izeye.helloworld.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * {@link Configuration} for testing.
 *
 * @author Johnny Lim
 */
@Configuration
public class TestConfig {

    @Bean
    @Profile({ "!profile1 & !profile2" })
    public UserService userService() {
        return new DefaultUserService();
    }

    @Bean
    @Profile({ "profile1", "profile2" })
    public UserService noopUserService() {
        return new NoopUserService();
    }

}
