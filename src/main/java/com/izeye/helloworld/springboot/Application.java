package com.izeye.helloworld.springboot;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * A sample application.
 *
 * @author Johnnhy Lim
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class Application {

	private final ApplicationContext applicationContext;

	public Application(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void init() {
		Arrays.stream(this.applicationContext.getBeanDefinitionNames()).sorted().forEach(log::info);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
