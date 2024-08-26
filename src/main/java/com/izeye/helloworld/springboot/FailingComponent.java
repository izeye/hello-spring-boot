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

    private final ComponentWithNonDaemonThread componentWithNonDaemonThread;

    public FailingComponent(ComponentWithNonDaemonThread componentWithNonDaemonThread) {
        this.componentWithNonDaemonThread = componentWithNonDaemonThread;

        throw new RuntimeException();
    }

    // This won't be invoked.
    @PreDestroy
    public void destroy() {
        log.info("Destroy...");
    }

}
