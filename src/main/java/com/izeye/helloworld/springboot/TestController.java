package com.izeye.helloworld.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link Controller} for testing.
 *
 * @author Johnny Lim
 */
@Controller
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping("/json")
    public ModelAndView modelAndViewJson() {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("firstName", "Johnny");
        return modelAndView;
    }

}
