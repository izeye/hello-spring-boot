package com.izeye.helloworld.springboot;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulingComponent {

    @PostConstruct
    public void init() {
        log.info("init() is invoked.");
    }

    @Scheduled(fixedRate = 5_000)
    public void scheduled() {
        log.info("scheduled() is invoked.");
    }

}
