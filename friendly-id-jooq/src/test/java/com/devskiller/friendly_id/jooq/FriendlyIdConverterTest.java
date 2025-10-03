package com.devskiller.friendly_id.jooq;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.devskiller.friendly_id.FriendlyId;

import static org.junit.jupiter.api.Assertions.*;

class FriendlyIdConverterTest {

	private final FriendlyIdConverter converter = new FriendlyIdConverter();

	@Test
	void shouldConvertUuidToFriendlyId() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");

		// when
		String friendlyId = converter.from(uuid);

		// then
		assertEquals(FriendlyId.toFriendlyId(uuid), friendlyId);
	}

	@Test
	void shouldConvertFriendlyIdToUuid() {
		// given
		String friendlyId = "5wbwf6yUxVBcr48AMbz9cb";

		// when
		UUID uuid = converter.to(friendlyId);

		// then
		assertEquals(FriendlyId.toUuid(friendlyId), uuid);
	}

	@Test
	void shouldHandleNullUuid() {
		// when
		String friendlyId = converter.from(null);

		// then
		assertNull(friendlyId);
	}

	@Test
	void shouldHandleNullFriendlyId() {
		// when
		UUID uuid = converter.to(null);

		// then
		assertNull(uuid);
	}

	@Test
	void shouldReturnCorrectFromType() {
		// when
		Class<UUID> fromType = converter.fromType();

		// then
		assertEquals(UUID.class, fromType);
	}

	@Test
	void shouldReturnCorrectToType() {
		// when
		Class<String> toType = converter.toType();

		// then
		assertEquals(String.class, toType);
	}

	@Test
	void shouldBeReversible() {
		// given
		UUID originalUuid = UUID.randomUUID();

		// when
		String friendlyId = converter.from(originalUuid);
		UUID convertedUuid = converter.to(friendlyId);

		// then
		assertEquals(originalUuid, convertedUuid);
	}
}
