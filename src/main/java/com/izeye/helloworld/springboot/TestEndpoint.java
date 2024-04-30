package com.izeye.helloworld.springboot;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

/**
 * {@link Endpoint} for testing.
 *
 * @author Johnny Lim
 */
@Component
@Endpoint(id = "test")
public class TestEndpoint {

    @ReadOperation
    public String findAll() {
        return "findAll()";
    }

    @ReadOperation
    public String findById(@Selector String id) {
        return "findById(): " + id;
    }

}
