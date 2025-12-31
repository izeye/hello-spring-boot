package com.izeye.helloworld.springboot;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * {@link ApplicationListener} for warm-up.
 *
 * @author Johnny Lim
 */
@Component
public class WarmUpApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // OUT_OF_SERVICE for readiness yet.
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + ": Warming up...");

            sleep();
        }

        // UP for readiness after this.

        // Trigger application failure.
//        throw new RuntimeException("Something went wrong!");
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

}
