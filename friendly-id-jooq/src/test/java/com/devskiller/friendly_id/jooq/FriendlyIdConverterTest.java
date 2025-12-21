package com.devskiller.friendly_id.jooq;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.devskiller.friendly_id.type.FriendlyId;

import static org.junit.jupiter.api.Assertions.*;

class FriendlyIdConverterTest {

	private final FriendlyIdConverter converter = new FriendlyIdConverter();

	@Test
	void shouldConvertUuidToFriendlyId() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");

		// when
		FriendlyId friendlyId = converter.from(uuid);

		// then
		assertEquals(uuid, friendlyId.uuid());
	}

	@Test
	void shouldConvertFriendlyIdToUuid() {
		// given
		FriendlyId friendlyId = FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb");

		// when
		UUID uuid = converter.to(friendlyId);

		// then
		assertEquals(friendlyId.uuid(), uuid);
	}

	@Test
	void shouldHandleNullUuid() {
		// when
		FriendlyId friendlyId = converter.from(null);

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
		Class<FriendlyId> toType = converter.toType();

		// then
		assertEquals(FriendlyId.class, toType);
	}

	@Test
	void shouldBeReversible() {
		// given
		UUID originalUuid = UUID.randomUUID();

		// when
		FriendlyId friendlyId = converter.from(originalUuid);
		UUID convertedUuid = converter.to(friendlyId);

		// then
		assertEquals(originalUuid, convertedUuid);
	}

	@Test
	void shouldConvertToStringWhenNeeded() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");
		FriendlyId friendlyId = converter.from(uuid);

		// when
		String friendlyIdString = friendlyId.toString();

		// then
		assertEquals(com.devskiller.friendly_id.FriendlyId.toFriendlyId(uuid), friendlyIdString);
	}
}
