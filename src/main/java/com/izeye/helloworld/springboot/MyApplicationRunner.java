package com.izeye.helloworld.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Sample {@link ApplicationRunner}.
 *
 * @author Johnny Lim
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        log.info("Hello, Spring Boot!");
    }

}
