package com.izeye.helloworld.springboot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

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
