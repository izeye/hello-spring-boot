package com.izeye.helloworld.springboot;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * {@link Configuration} for web.
 *
 * @author Johnny Lim
 */
@Configuration
public class WebConfig {

    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    @Bean
    public FilterRegistrationBean<TeeFilter> teeFilter() {
        FilterRegistrationBean<TeeFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new TeeFilter());
        return filterRegistrationBean;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return (factory) -> {
            LogbackValve logbackValve = new LogbackValve();
            logbackValve.setFilename("logback-access.xml");

            factory.addContextValves(logbackValve);
        };
    }

}
