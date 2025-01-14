package com.izeye.helloworld.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/test-rest")
public class TestRestController {

    private static final Logger log = LoggerFactory.getLogger(TestRestController.class);

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

    @GetMapping("/my-request")
    public MyRequest getMyRequest(HttpServletRequest httpServletRequest) {
        MyRequest myRequest = new MyRequest();
        myRequest.setId("id");
        myRequest.setHttpServletRequest(httpServletRequest);
        return myRequest;
    }

}
