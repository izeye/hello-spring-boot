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

    @GetMapping(path = "/small-data-with-error")
    public String smallDataWithError(Model model) {
        model.addAttribute("data", "a".repeat(1_000));
        return "template_with_data_and_error";
    }

    // org.springframework.http.converter.HttpMessageNotWritableException: No converter for [class java.util.LinkedHashMap] with preset Content-Type 'text/html;charset=UTF-8'
    @GetMapping(path = "/large-data-with-error")
    public String largeDataWithError(Model model) {
        model.addAttribute("data", "a".repeat(100_000));
        return "template_with_data_and_error";
    }

}
