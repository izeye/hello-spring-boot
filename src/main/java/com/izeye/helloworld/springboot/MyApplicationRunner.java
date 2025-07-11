package com.izeye.helloworld.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Sample {@link ApplicationRunner}.
 *
 * @author Johnny Lim
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MyApplicationRunner.class);

    private final JdbcTemplate jdbcTemplate;

    public MyApplicationRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        Long count = this.jdbcTemplate.queryForObject("SELECT COUNT(1) FROM user", Long.class);
        log.info("Count: {}", count);
    }

}
