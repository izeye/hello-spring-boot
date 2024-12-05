package com.izeye.helloworld.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configuration for {@link org.springframework.scheduling.annotation.Async}.
 *
 * @author Johnny Lim
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return createThreadPoolTaskExecutor();
    }

    private static ThreadPoolTaskExecutor createThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
        return executor;
    }

    @Bean
    public ThreadPoolTaskExecutor anotherThreadPoolTaskExecutor() {
        return createThreadPoolTaskExecutor();
    }

}
