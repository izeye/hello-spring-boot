package com.izeye.helloworld.springboot;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Component that hangs when being destroyed.
 *
 * @author Johnny Lim
 */
@Slf4j
public class DestroyHangComponent {

    public void destroy() throws InterruptedException {
        log.info("Sleeping for 60 seconds...");
        TimeUnit.SECONDS.sleep(60);
    }

}
