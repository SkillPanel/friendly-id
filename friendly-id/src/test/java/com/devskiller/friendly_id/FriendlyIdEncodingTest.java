package com.devskiller.friendly_id;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the wire format of both encodings against vectors generated with released
 * artifacts: LEGACY vectors come from friendly-id 1.0.4 (ElegantPairing era),
 * STANDARD vectors from 1.1.0/2.0.0-beta5 (BigIntegerPairing). These must never
 * change — consumers persist FriendlyIds externally.
 */
class FriendlyIdEncodingTest {

	private static final UUID UUID_1 = UUID.fromString("d493e6d3-6a6a-4cf2-8990-46366c75064f");
	private static final UUID UUID_2 = UUID.fromString("1024138f-684f-4311-b539-774fd3ef3a9c");
	private static final UUID UUID_ZERO = UUID.fromString("00000000-0000-0000-0000-000000000000");
	private static final UUID UUID_ALL_BITS = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");
	private static final UUID UUID_LSB_SIGN_BIT = UUID.fromString("00000000-0000-0000-8000-000000000000");

	@AfterEach
	void restoreDefaultEncoding() {
		FriendlyIds.setEncoding(FriendlyIdEncoding.STANDARD);
	}

	@Test
	void standardEncodingIsTheDefault() {
		assertThat(FriendlyIds.getEncoding()).isEqualTo(FriendlyIdEncoding.STANDARD);
	}

	@Test
	void standardEncodingMatchesVectorsFrom110AndNewer() {
		assertThat(FriendlyIds.toFriendlyId(UUID_1)).isEqualTo("6T7xno6b7EKyT6xrj2iJo7");
		assertThat(FriendlyIds.toFriendlyId(UUID_2)).isEqualTo("USMZy4J8qe2K9yZBxR4Hs");
		assertThat(FriendlyIds.toFriendlyId(UUID_ZERO)).isEqualTo("0");
		assertThat(FriendlyIds.toFriendlyId(UUID_ALL_BITS)).isEqualTo("7n42DGM5Tflk9n8mt7Fhc7");
		assertThat(FriendlyIds.toFriendlyId(UUID_LSB_SIGN_BIT)).isEqualTo("AzL8n0Y58m8");
	}

	@Test
	void legacyEncodingMatchesVectorsFrom104() {
		FriendlyIds.setEncoding(FriendlyIdEncoding.LEGACY);

		assertThat(FriendlyIds.toFriendlyId(UUID_1)).isEqualTo("6fZlmOHtnSlxGJGX7SMFg4");
		assertThat(FriendlyIds.toFriendlyId(UUID_2)).isEqualTo("2er4Hm3VwGW3J0lfwrvQYd");
		assertThat(FriendlyIds.toFriendlyId(UUID_ZERO)).isEqualTo("0");
		assertThat(FriendlyIds.toFriendlyId(UUID_ALL_BITS)).isEqualTo("3");
		assertThat(FriendlyIds.toFriendlyId(UUID_LSB_SIGN_BIT)).isEqualTo("7n42DGM5Tfl2CQZcquv8Vd");
	}

	@Test
	void legacyDecodingMatchesVectorsFrom104() {
		FriendlyIds.setEncoding(FriendlyIdEncoding.LEGACY);

		assertThat(FriendlyIds.toUuid("6fZlmOHtnSlxGJGX7SMFg4")).isEqualTo(UUID_1);
		assertThat(FriendlyIds.toUuid("2er4Hm3VwGW3J0lfwrvQYd")).isEqualTo(UUID_2);
		assertThat(FriendlyIds.toUuid("3")).isEqualTo(UUID_ALL_BITS);
	}

	@Test
	void legacyEncodingIsReversible() {
		FriendlyIds.setEncoding(FriendlyIdEncoding.LEGACY);

		for (int i = 0; i < 1000; i++) {
			UUID uuid = UUID.randomUUID();
			assertThat(FriendlyIds.toUuid(FriendlyIds.toFriendlyId(uuid))).isEqualTo(uuid);
		}
	}

	@Test
	void encodingSwitchAffectsAllEntryPoints() {
		FriendlyIds.setEncoding(FriendlyIdEncoding.LEGACY);

		assertThat(com.devskiller.friendly_id.type.FriendlyId.of(UUID_1).value())
				.isEqualTo("6fZlmOHtnSlxGJGX7SMFg4");
		assertThat(com.devskiller.friendly_id.type.FriendlyId.parse("6fZlmOHtnSlxGJGX7SMFg4").toUuid())
				.isEqualTo(UUID_1);
	}

}
