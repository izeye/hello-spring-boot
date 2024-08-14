package com.izeye.helloworld.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * {@link Controller} for testing Thymeleaf.
 *
 * @author Johnny Lim
 */
@Controller
@RequestMapping(path = "/test-thymeleaf")
public class ThymeleafTestController {

    @GetMapping
    public String index(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

}
