package com.devskiller.friendly_id.jpa;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.devskiller.friendly_id.type.FriendlyId;

import static org.junit.jupiter.api.Assertions.*;

class FriendlyIdConverterTest {

	private final FriendlyIdConverter converter = new FriendlyIdConverter();

	@Test
	void shouldConvertFriendlyIdToUuid() {
		// given
		FriendlyId friendlyId = FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb");

		// when
		UUID uuid = converter.convertToDatabaseColumn(friendlyId);

		// then
		assertEquals(friendlyId.uuid(), uuid);
	}

	@Test
	void shouldConvertUuidToFriendlyId() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");

		// when
		FriendlyId friendlyId = converter.convertToEntityAttribute(uuid);

		// then
		assertEquals(uuid, friendlyId.uuid());
	}

	@Test
	void shouldHandleNullFriendlyId() {
		// when
		UUID uuid = converter.convertToDatabaseColumn(null);

		// then
		assertNull(uuid);
	}

	@Test
	void shouldHandleNullUuid() {
		// when
		FriendlyId friendlyId = converter.convertToEntityAttribute(null);

		// then
		assertNull(friendlyId);
	}

	@Test
	void shouldBeReversible() {
		// given
		UUID originalUuid = UUID.randomUUID();

		// when
		FriendlyId friendlyId = converter.convertToEntityAttribute(originalUuid);
		UUID convertedUuid = converter.convertToDatabaseColumn(friendlyId);

		// then
		assertEquals(originalUuid, convertedUuid);
	}

	@Test
	void shouldConvertToStringWhenNeeded() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");
		FriendlyId friendlyId = converter.convertToEntityAttribute(uuid);

		// when
		String friendlyIdString = friendlyId.toString();

		// then
		assertEquals(com.devskiller.friendly_id.FriendlyId.toFriendlyId(uuid), friendlyIdString);
	}
}
