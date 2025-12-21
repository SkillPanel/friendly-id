package com.devskiller.friendly_id.spring;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.devskiller.friendly_id.FriendlyId;

import static com.devskiller.friendly_id.spring.ObjectMapperConfiguration.mapper;
import static org.assertj.core.api.Assertions.assertThat;

class FriendlyIdDeserializerTest {

	@Test
	void shouldSerializeFriendlyId() throws Exception {
		UUID uuid = UUID.randomUUID();
		String json = mapper().writeValueAsString(uuid);
		System.out.println(json);
		assertThat(json).contains(FriendlyId.toFriendlyId(uuid));
	}

	@Test
	void shouldDeserializeFriendlyId() throws Exception {
		String friendlyId = "2YSfgVHnEYbYgfFKhEX3Sz";
		UUID uuid = mapper().readValue("\"" + friendlyId + "\"", UUID.class);
		assertThat(uuid).isEqualByComparingTo(FriendlyId.toUuid(friendlyId));
	}
}
