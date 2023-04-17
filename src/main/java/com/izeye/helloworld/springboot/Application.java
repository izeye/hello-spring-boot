package com.izeye.helloworld.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		String fileEncoding = System.getProperty("file.encoding");
		log.info("file.encoding: {}", fileEncoding);

		log.info("테스트");

		if (!"UTF-8".equals(fileEncoding)) {
			throw new IllegalStateException("'file.encoding' should be UTF-8, but was " + fileEncoding);
		}

		SpringApplication.run(Application.class, args);
	}

}
