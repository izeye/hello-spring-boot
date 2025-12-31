package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * {@link BeforeAllCallback} for unsetting {@code SPRING_PROFILES_ACTIVE} environment variable.
 *
 * @author Johnny Lim
 */
public class SpringProfilesActiveUnsettingBeforeAllCallback implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        // This doesn't work with Spring Cloud Config.
        System.setProperty("spring.profiles.active", "");
    }

}
