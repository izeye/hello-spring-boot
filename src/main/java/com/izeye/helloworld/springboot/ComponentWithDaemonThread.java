package com.izeye.helloworld.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Component with a daemon thread.
 *
 * @author Johnny Lim
 */
@Component
@Slf4j
public class ComponentWithDaemonThread {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public ComponentWithDaemonThread() {
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

}
