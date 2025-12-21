package com.devskiller.friendly_id;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.RepeatedTest;

import static com.devskiller.friendly_id.FriendlyId.toFriendlyId;
import static com.devskiller.friendly_id.FriendlyId.toUuid;
import static com.devskiller.friendly_id.IdUtil.areEqualIgnoringLeadingZeros;
import static org.assertj.core.api.Assertions.assertThat;

class FriendlyIdTest {

	@RepeatedTest(1000)
	void shouldCreateValidIdsThatConformToUuidType4() {
		UUID uuid = toUuid(FriendlyId.createFriendlyId());
		assertThat(uuid.version()).isEqualTo(4);
	}

	@RepeatedTest(1000)
	void encodingUuidShouldBeReversible() {
		UUID uuid = UUID.randomUUID();
		UUID result = toUuid(toFriendlyId(uuid));
		assertThat(result).isEqualTo(uuid);
	}

	@RepeatedTest(1000)
	void decodingIdShouldBeReversible() {
		String id = generateRandomFriendlyId();
		String result = toFriendlyId(toUuid(id));
		assertThat(areEqualIgnoringLeadingZeros(result, id)).isTrue();
	}

	private String generateRandomFriendlyId() {
		Random random = new Random();
		BigInteger bigInt = new BigInteger(128, random);
		return Base62.encode(bigInt);
	}

}