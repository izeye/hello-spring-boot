package com.izeye.helloworld.springboot;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Failing component.
 *
 * @author Johnny Lim
 */
@Component
@Slf4j
public class FailingComponent {

    private final ComponentWithDaemonThread componentWithDaemonThread;

    public FailingComponent(ComponentWithDaemonThread componentWithDaemonThread) {
        this.componentWithDaemonThread = componentWithDaemonThread;

        throw new RuntimeException();
    }

    // This won't be invoked.
    @PreDestroy
    public void destroy() {
        log.info("Destroy...");
    }

}
