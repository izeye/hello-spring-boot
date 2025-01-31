/*
 * Copyright 2016 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.izeye.helloworld.springboot;

import ch.qos.logback.access.common.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for web.
 *
 * @author Johnny Lim
 */
@Configuration
public class WebConfig {

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
