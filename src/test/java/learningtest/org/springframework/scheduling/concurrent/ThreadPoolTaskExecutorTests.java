package learningtest.org.springframework.scheduling.concurrent;

import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatNoException;

/**
 * Tests for {@link ThreadPoolTaskExecutor}.
 *
 * @author Johnny Lim
 */
class ThreadPoolTaskExecutorTests {

    @Test
    void expectTaskRejectedException() {
        CountDownLatch latch = new CountDownLatch(1);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(0);
        executor.afterPropertiesSet();

        Runnable task = () -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        executor.submit(task);
        assertThatException().isThrownBy(() -> executor.submit(task)).isExactlyInstanceOf(TaskRejectedException.class);
    }

    @Test
    void ignoreTaskRejectedException() {
        CountDownLatch latch = new CountDownLatch(1);

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(1);
        threadPoolTaskExecutor.setQueueCapacity(0);
        threadPoolTaskExecutor.setRejectedExecutionHandler((r, executor) -> {
            System.out.println("Expected!");
        });
        threadPoolTaskExecutor.afterPropertiesSet();

        Runnable task = () -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        threadPoolTaskExecutor.submit(task);
        assertThatNoException().isThrownBy(() -> threadPoolTaskExecutor.submit(task));
    }

}
