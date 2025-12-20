package com.devskiller.friendly_id.spring;

import java.util.UUID;

import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static com.devskiller.friendly_id.spring.ObjectMapperConfiguration.mapper;
import static org.assertj.core.api.Assertions.assertThat;

class FieldWithoutFriendlyIdTest {

	private final UUID uuid = UUID.fromString("f088ce5b-9279-4cc3-946a-c15ad740dd6d");
	private JsonMapper jsonMapper = mapper();

	@Test
	void shouldAllowToDoNotCodeUuidInDataObject() {
		Foo foo = new Foo();
		foo.setRawUuid(uuid);
		foo.setFriendlyId(uuid);

		String json = jsonMapper.writeValueAsString(foo);

		// JSON field order may vary, so check each field separately
		assertThat(json).contains("\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
		assertThat(json).contains("\"friendlyId\":\"7Jsg6CPDscHawyJfE70b9x\"");

		Foo cloned = jsonMapper.readValue(json, Foo.class);
		assertThat(cloned.getRawUuid()).isEqualTo(foo.getFriendlyId());
	}

	@Test
	void shouldDeserializeUuidsInDataObject() {
		String json = "{\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\",\"friendlyId\":\"7Jsg6CPDscHawyJfE70b9x\"}";

		Foo cloned = jsonMapper.readValue(json, Foo.class);
		assertThat(cloned.getRawUuid()).isEqualTo(uuid);
		assertThat(cloned.getFriendlyId()).isEqualTo(uuid);
	}


	@Test
	void shouldSerializeUuidsInValueObject() {
		jsonMapper = mapper();

		Bar bar = new Bar(uuid, uuid);

		String json = jsonMapper.writeValueAsString(bar);

		assertThat(json).isEqualToIgnoringWhitespace(
				"{\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\",\"friendlyId\":\"7Jsg6CPDscHawyJfE70b9x\"}"
		);
	}

	@Test
	void shouldDeserializeUuuidsValueObject() {
		jsonMapper = mapper();

		String json = "{\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\",\"friendlyId\":\"7Jsg6CPDscHawyJfE70b9x\"}";

		Bar deserialized = jsonMapper.readValue(json, Bar.class);

		assertThat(deserialized.getRawUuid()).isEqualTo(uuid);
		assertThat(deserialized.getFriendlyId()).isEqualTo(uuid);
	}

}
