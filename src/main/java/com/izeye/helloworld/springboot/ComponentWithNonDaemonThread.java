package com.izeye.helloworld.springboot;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Component with a non-daemon thread.
 *
 * @author Johnny Lim
 */
@Component
@Slf4j
public class ComponentWithNonDaemonThread {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ComponentWithNonDaemonThread() {
        this.executorService.submit(() -> {
            try {
                while (true) {
                    log.info("Working...");

                    Thread.sleep(1_000);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @PreDestroy
    public void destroy() {
        log.info("Destroying...");

        this.executorService.shutdownNow();
    }

}
