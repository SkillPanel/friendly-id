package com.devskiller.friendly_id.openfeign;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.devskiller.friendly_id.type.FriendlyId;

import feign.codec.Decoder;

import static com.devskiller.friendly_id.FriendlyIds.toFriendlyId;
import static org.assertj.core.api.Assertions.assertThat;

class FriendlyIdDecoderTest {

	@Test
	void shouldDecodeFriendlyIdStringToUuid() throws IOException {
		// given
		UUID expectedUuid = UUID.fromString("c3587ec5-0976-497f-8374-61e0c2ea3da5");
		String friendlyIdString = toFriendlyId(expectedUuid);

		Decoder delegateDecoder = (response, type) -> friendlyIdString;
		FriendlyIdDecoder decoder = new FriendlyIdDecoder(delegateDecoder);

		// when
		Object result = decoder.decode(null, UUID.class);

		// then
		assertThat(result).isEqualTo(expectedUuid);
	}

	@Test
	void shouldDecodeFriendlyIdStringToFriendlyIdValueObject() throws IOException {
		// given
		UUID uuid = UUID.fromString("c3587ec5-0976-497f-8374-61e0c2ea3da5");
		String friendlyIdString = toFriendlyId(uuid);

		Decoder delegateDecoder = (response, type) -> friendlyIdString;
		FriendlyIdDecoder decoder = new FriendlyIdDecoder(delegateDecoder);

		// when
		Object result = decoder.decode(null, FriendlyId.class);

		// then
		assertThat(result)
				.isInstanceOf(FriendlyId.class)
				.extracting(fid -> ((FriendlyId) fid).uuid())
				.isEqualTo(uuid);
	}

	@Test
	void shouldDelegateOtherTypes() throws IOException {
		// given
		String regularString = "test";
		Decoder delegateDecoder = (response, type) -> regularString;
		FriendlyIdDecoder decoder = new FriendlyIdDecoder(delegateDecoder);

		// when
		Object result = decoder.decode(null, String.class);

		// then
		assertThat(result).isEqualTo(regularString);
	}

	@Test
	void shouldDelegateWhenResponseIsNotString() throws IOException {
		// given
		Integer number = 42;
		Decoder delegateDecoder = (response, type) -> number;
		FriendlyIdDecoder decoder = new FriendlyIdDecoder(delegateDecoder);

		// when
		Object result = decoder.decode(null, UUID.class);

		// then
		assertThat(result).isEqualTo(number);
	}
}
