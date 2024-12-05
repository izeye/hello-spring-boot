package com.izeye.helloworld.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

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
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(new NamedThreadFactory("primary"));
        return executor;
    }

    @Bean
    public ThreadPoolTaskExecutor anotherThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(new NamedThreadFactory("another"));
        return executor;
    }

    static class NamedThreadFactory implements ThreadFactory {

        private final AtomicInteger sequence = new AtomicInteger(1);

        private final String prefix;

        NamedThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            int seq = this.sequence.getAndIncrement();
            thread.setName(this.prefix + (seq > 1 ? "-" + seq : ""));
            thread.setDaemon(true);
            return thread;
        }

    }

}
