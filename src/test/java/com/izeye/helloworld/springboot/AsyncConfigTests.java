package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AsyncConfig}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class AsyncConfigTests {

    @Autowired
    AsyncService asyncService;

    @Test
    void test() throws InterruptedException {
        this.asyncService.async();

        DefaultAsyncService defaultAsyncService = (DefaultAsyncService) AopProxyUtils.getSingletonTarget(this.asyncService);

        assertThat(defaultAsyncService.latch.await(1, TimeUnit.SECONDS)).isTrue();
        assertThat(defaultAsyncService.thread.getName()).contains("primary");
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        DefaultAsyncService asyncService() {
            return new DefaultAsyncService();
        }

    }

    interface AsyncService {

        @Async
        void async();

    }

    static class DefaultAsyncService implements AsyncService {

        CountDownLatch latch = new CountDownLatch(1);

        volatile Thread thread;

        @Override
        public void async() {
            this.thread = Thread.currentThread();

            this.latch.countDown();
        }

    }

}
