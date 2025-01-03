package com.izeye.helloworld.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * {@link Configuration} for scheduling.
 *
 * @author Johnny Lim
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    private static final Logger log = LoggerFactory.getLogger(SchedulingConfig.class);

    @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
    public void greet() {
        log.info("Hello, world!");
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
    public void sleep1() throws InterruptedException {
        log.info("Sleeping!");
        TimeUnit.SECONDS.sleep(1);
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
    public void sleep2() throws InterruptedException {
        log.info("Sleeping!");
        TimeUnit.SECONDS.sleep(1);
    }

}
