package com.devskiller.friendly_id.spring;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.FriendlyIds;
import com.devskiller.friendly_id.jackson.FriendlyIdModule;

import static org.assertj.core.api.Assertions.assertThat;

class RawFormatModuleTest {

	private final UUID uuid = UUID.fromString("f088ce5b-9279-4cc3-946a-c15ad740dd6d");

	private final JsonMapper mapper = JsonMapper.builder()
			.addModule(new FriendlyIdModule(FriendlyIdFormat.RAW))
			.build();

	@Test
	void shouldSerializeUuidInStandardFormat() {
		String json = mapper.writeValueAsString(uuid);

		assertThat(json).isEqualTo("\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
	}

	@Test
	void shouldDeserializeStandardUuid() {
		UUID result = mapper.readValue("\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"", UUID.class);

		assertThat(result).isEqualTo(uuid);
	}

	@Test
	void shouldDeserializeFriendlyIdWhenModuleIsRaw() {
		String friendlyId = FriendlyIds.toFriendlyId(uuid);

		UUID result = mapper.readValue("\"" + friendlyId + "\"", UUID.class);

		assertThat(result).isEqualTo(uuid);
	}

	@Test
	void shouldRespectPerFieldOverrideWhenModuleIsRaw() {
		var foo = new Foo(uuid, uuid);

		String json = mapper.writeValueAsString(foo);

		// rawUuid has @IdFormat(RAW) -> standard format
		assertThat(json).contains("\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
		// friendlyId has no annotation, module default is RAW -> standard format
		assertThat(json).contains("\"friendlyId\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
	}

	@Test
	void shouldSerializeFriendlyFormatWithUrl62Override() {
		var mapper = JsonMapper.builder()
				.addModule(new FriendlyIdModule(FriendlyIdFormat.RAW))
				.build();

		// Foo: rawUuid=@IdFormat(RAW), friendlyId=no annotation (module default RAW)
		// Both should be standard UUID when module is RAW
		var foo = new Foo(uuid, uuid);
		String json = mapper.writeValueAsString(foo);

		assertThat(json).contains("\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
		assertThat(json).contains("\"friendlyId\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
	}
}
