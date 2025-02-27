package com.project.challenge;

import com.project.challenge.config.EmbededRedis;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {EmbededRedis .class})
class ChallengeApplicationTests {

	@Test
	void contextLoads() {
	}

}
