package com.izeye.helloworld.springboot;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping("/httpServletRequest")
    public String http(HttpServletRequest request) {
        return "name='%s'".formatted(request.getParameter("name"));
    }

}
