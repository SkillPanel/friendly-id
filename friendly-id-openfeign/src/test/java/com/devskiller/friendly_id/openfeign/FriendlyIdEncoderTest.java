package com.devskiller.friendly_id.openfeign;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.devskiller.friendly_id.type.FriendlyId;

import feign.RequestTemplate;
import feign.codec.Encoder;

import static com.devskiller.friendly_id.FriendlyIds.toFriendlyId;
import static org.assertj.core.api.Assertions.assertThat;

class FriendlyIdEncoderTest {

	@Test
	void shouldEncodeUuidAsFriendlyId() {
		// given
		UUID uuid = UUID.fromString("c3587ec5-0976-497f-8374-61e0c2ea3da5");
		String expectedFriendlyId = toFriendlyId(uuid);

		String[] capturedValue = new String[1];
		Encoder delegateEncoder = (object, bodyType, template)
				-> capturedValue[0] = (String) object;
		FriendlyIdEncoder encoder = new FriendlyIdEncoder(delegateEncoder);
		RequestTemplate template = new RequestTemplate();

		// when
		encoder.encode(uuid, UUID.class, template);

		// then
		assertThat(capturedValue[0]).isEqualTo(expectedFriendlyId);
	}

	@Test
	void shouldEncodeFriendlyIdValueObjectAsString() {
		// given
		UUID uuid = UUID.fromString("c3587ec5-0976-497f-8374-61e0c2ea3da5");
		FriendlyId friendlyId = FriendlyId.of(uuid);
		String expectedString = friendlyId.toString();

		String[] capturedValue = new String[1];
		Encoder delegateEncoder = (object, bodyType, template)
				-> capturedValue[0] = (String) object;
		FriendlyIdEncoder encoder = new FriendlyIdEncoder(delegateEncoder);
		RequestTemplate template = new RequestTemplate();

		// when
		encoder.encode(friendlyId, FriendlyId.class, template);

		// then
		assertThat(capturedValue[0]).isEqualTo(expectedString);
	}

	@Test
	void shouldDelegateOtherTypes() {
		// given
		String regularString = "test";
		
		String[] capturedValue = new String[1];
		Encoder delegateEncoder = (object, bodyType, template)
				-> capturedValue[0] = (String) object;
		FriendlyIdEncoder encoder = new FriendlyIdEncoder(delegateEncoder);
		RequestTemplate template = new RequestTemplate();

		// when
		encoder.encode(regularString, String.class, template);

		// then
		assertThat(capturedValue[0]).isEqualTo(regularString);
	}
}
