package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

/**
 * Sample request.
 *
 * @author Johnny Lim
 */
@Data
public class MyRequest {

    private String id;

    @JsonIgnore
    private HttpServletRequest httpServletRequest;

}
