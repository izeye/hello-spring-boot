package com.izeye.helloworld.springboot;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.Map;

/**
 * {@link Controller} for testing {@link Controller}.
 *
 * @author Johnny Lim
 */
@Controller
@RequestMapping(path = "/test-controller")
public class ControllerTestingController {

    @GetMapping("/model-and-view/missing-model")
    public ModelAndView modelAndViewMissingModel() {
        return new ModelAndView(new MappingJackson2JsonView());
    }

    @GetMapping("/model-and-view/custom-view")
    public ModelAndView modelAndViewCustomView() {
        return new ModelAndView((model, request, response) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        });
    }

    @GetMapping("/response-body/empty-map")
    @ResponseBody
    public Map<String, Object> responseBodyEmptyMap() {
        return Collections.emptyMap();
    }

}
