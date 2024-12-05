package com.izeye.helloworld.springboot;

import org.springframework.scheduling.annotation.Async;

/**
 * Service interface for testing.
 *
 * @author Johnny Lim
 */
public interface TestService {

    @Async
    void test();

    @Async("anotherThreadPoolTaskExecutor")
    void testAnother();

}
