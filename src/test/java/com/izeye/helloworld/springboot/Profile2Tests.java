package com.izeye.helloworld.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests having {@code profile2} Spring profile.
 *
 * @author Johnny Lim
 */
@SpringBootTest
@ActiveProfiles("profile2")
class Profile2Tests {

	@Autowired
	private UserService userService;

	@Test
	void test() {
		assertThat(this.userService).isExactlyInstanceOf(NoopUserService.class);
	}

}
