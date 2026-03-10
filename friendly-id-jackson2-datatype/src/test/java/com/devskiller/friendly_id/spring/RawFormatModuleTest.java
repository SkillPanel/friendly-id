package com.devskiller.friendly_id.spring;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.FriendlyIds;
import com.devskiller.friendly_id.jackson2.FriendlyIdJackson2Module;

import static org.assertj.core.api.Assertions.assertThat;

class RawFormatModuleTest {

	private final UUID uuid = UUID.fromString("f088ce5b-9279-4cc3-946a-c15ad740dd6d");

	private final ObjectMapper mapper = new ObjectMapper()
			.registerModule(new FriendlyIdJackson2Module(FriendlyIdFormat.UUID));

	@Test
	void shouldSerializeUuidInStandardFormat() throws Exception {
		String json = mapper.writeValueAsString(uuid);

		assertThat(json).isEqualTo("\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
	}

	@Test
	void shouldDeserializeStandardUuid() throws Exception {
		UUID result = mapper.readValue("\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"", UUID.class);

		assertThat(result).isEqualTo(uuid);
	}

	@Test
	void shouldDeserializeFriendlyIdWhenModuleIsRaw() throws Exception {
		String friendlyId = FriendlyIds.toFriendlyId(uuid);

		UUID result = mapper.readValue("\"" + friendlyId + "\"", UUID.class);

		assertThat(result).isEqualTo(uuid);
	}

	@Test
	void shouldRespectPerFieldAnnotationWhenModuleIsRaw() throws Exception {
		Foo foo = new Foo();
		foo.setRawUuid(uuid);
		foo.setFriendlyId(uuid);

		String json = mapper.writeValueAsString(foo);

		// rawUuid has @IdFormat(UUID) -> standard format
		assertThat(json).contains("\"rawUuid\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
		// friendlyId has no annotation, module default is UUID -> standard format
		assertThat(json).contains("\"friendlyId\":\"f088ce5b-9279-4cc3-946a-c15ad740dd6d\"");
	}
}
