package com.devskiller.friendly_id.sample.simple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ApplicationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void shouldSerialize() {
		// given
		UUID uuid = UUID.randomUUID();

		// when
		Bar entity = restTemplate.getForEntity("/bars/{id}", Bar.class, uuid).getBody();

		// then
		then(entity).isNotNull();
		then(entity.getId()).isEqualTo(uuid);
	}
}
