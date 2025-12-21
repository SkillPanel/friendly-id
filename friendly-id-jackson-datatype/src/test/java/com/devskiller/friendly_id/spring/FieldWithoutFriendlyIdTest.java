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
		var foo = new Foo(uuid, uuid);

		var json = jsonMapper.writeValueAsString(foo);

		// JSON field order may vary, so check each field separately
		assertThat(json).contains("\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
		assertThat(json).contains("\"friendlyId\":\"7Jsg6CPDscHawyJfE70b9x\"");

		var cloned = jsonMapper.readValue(json, Foo.class);
		assertThat(cloned.rawUuid()).isEqualTo(foo.friendlyId());
	}

	@Test
	void shouldDeserializeUuidsInDataObject() {
		var json = """
				{"rawUuid":"f088ce5b-9279-4cc3-946a-c15ad740dd6d","friendlyId":"7Jsg6CPDscHawyJfE70b9x"}""";

		var cloned = jsonMapper.readValue(json, Foo.class);
		assertThat(cloned.rawUuid()).isEqualTo(uuid);
		assertThat(cloned.friendlyId()).isEqualTo(uuid);
	}

	@Test
	void shouldSerializeUuidsInValueObject() {
		jsonMapper = mapper();

		var bar = new Bar(uuid, uuid);

		var json = jsonMapper.writeValueAsString(bar);

		assertThat(json).isEqualToIgnoringWhitespace("""
				{"rawUuid":"f088ce5b-9279-4cc3-946a-c15ad740dd6d","friendlyId":"7Jsg6CPDscHawyJfE70b9x"}""");
	}

	@Test
	void shouldDeserializeUuidsInValueObject() {
		jsonMapper = mapper();

		var json = """
				{"rawUuid":"f088ce5b-9279-4cc3-946a-c15ad740dd6d","friendlyId":"7Jsg6CPDscHawyJfE70b9x"}""";

		var deserialized = jsonMapper.readValue(json, Bar.class);

		assertThat(deserialized.rawUuid()).isEqualTo(uuid);
		assertThat(deserialized.friendlyId()).isEqualTo(uuid);
	}
}
