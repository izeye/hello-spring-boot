package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests having another Spring profile.
 *
 * @author Johnny Lim
 */
@SpringBootTest
@ActiveProfiles("profile3")
class OtherProfileTests {

	@Autowired
	private UserService userService;

	@Test
	void test() {
		assertThat(this.userService).isExactlyInstanceOf(DefaultUserService.class);
	}

}
