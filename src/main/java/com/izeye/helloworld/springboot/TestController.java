package com.izeye.helloworld.springboot;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/httpServletRequest")
    public String http(HttpServletRequest request) {
        return "name='%s'".formatted(request.getParameter("name"));
    }

    @GetMapping("/sleep")
    public String sleep(@RequestParam int seconds) throws InterruptedException {
        log.info("I'll sleep for {} seconds.", seconds);
        TimeUnit.SECONDS.sleep(seconds);
        log.info("I slept for {} seconds.", seconds);
        return "I slept for %d seconds.".formatted(seconds);
    }

}