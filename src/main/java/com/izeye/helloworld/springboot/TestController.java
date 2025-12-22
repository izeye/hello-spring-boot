package com.izeye.helloworld.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/path-variable/{id}")
    public String getPathVariable(@PathVariable String id) {
        return id;
    }

}
