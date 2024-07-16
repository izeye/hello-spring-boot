package com.izeye.helloworld.springboot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties} for testing.
 *
 * @author Johnny Lim
 */
@ConfigurationProperties("test")
@Data
public class TestProperties {

    private String name;

    private String value;

}
