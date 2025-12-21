package com.devskiller.friendly_id.type;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FriendlyIdTest {

	@Test
	void shouldCreateFromUuid() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");

		// when
		FriendlyId friendlyId = FriendlyId.of(uuid);

		// then
		assertEquals(uuid, friendlyId.uuid());
	}

	@Test
	void shouldCreateFromString() {
		// given
		String friendlyIdString = "5wbwf6yUxVBcr48AMbz9cb";

		// when
		FriendlyId friendlyId = FriendlyId.fromString(friendlyIdString);

		// then
		assertEquals(friendlyIdString, friendlyId.toString());
	}

	@Test
	void shouldCreateRandom() {
		// when
		FriendlyId friendlyId1 = FriendlyId.random();
		FriendlyId friendlyId2 = FriendlyId.random();

		// then
		assertNotEquals(friendlyId1, friendlyId2);
		assertNotNull(friendlyId1.uuid());
		assertNotNull(friendlyId2.uuid());
	}

	@Test
	void shouldConvertToString() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");
		FriendlyId friendlyId = FriendlyId.of(uuid);

		// when
		String result = friendlyId.toString();

		// then
		assertEquals(com.devskiller.friendly_id.FriendlyId.toFriendlyId(uuid), result);
	}

	@Test
	void shouldBeEqualWhenUuidIsEqual() {
		// given
		UUID uuid = UUID.randomUUID();
		FriendlyId id1 = FriendlyId.of(uuid);
		FriendlyId id2 = FriendlyId.of(uuid);

		// then
		assertEquals(id1, id2);
		assertEquals(id1.hashCode(), id2.hashCode());
	}

	@Test
	void shouldNotBeEqualWhenUuidIsDifferent() {
		// given
		FriendlyId id1 = FriendlyId.random();
		FriendlyId id2 = FriendlyId.random();

		// then
		assertNotEquals(id1, id2);
	}

	@Test
	void shouldBeComparable() {
		// given
		UUID uuid1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
		UUID uuid2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
		FriendlyId id1 = FriendlyId.of(uuid1);
		FriendlyId id2 = FriendlyId.of(uuid2);

		// then
		assertTrue(id1.compareTo(id2) < 0);
		assertTrue(id2.compareTo(id1) > 0);
		assertEquals(0, id1.compareTo(id1));
	}

	@Test
	void shouldThrowExceptionWhenUuidIsNull() {
		// when/then
		assertThrows(NullPointerException.class, () -> FriendlyId.of(null));
	}

	@Test
	void shouldThrowExceptionWhenStringIsNull() {
		// when/then
		assertThrows(NullPointerException.class, () -> FriendlyId.fromString(null));
	}

	@Test
	void shouldBeReversible() {
		// given
		UUID originalUuid = UUID.randomUUID();
		FriendlyId friendlyId = FriendlyId.of(originalUuid);

		// when
		String friendlyIdString = friendlyId.toString();
		FriendlyId reconstructed = FriendlyId.fromString(friendlyIdString);

		// then
		assertEquals(friendlyId, reconstructed);
		assertEquals(originalUuid, reconstructed.uuid());
	}

	@Test
	void shouldBeEqualRegardlessOfCreationMethod() {
		// given
		UUID uuid = UUID.fromString("7b0f3a3e-3b3a-4b3a-8b3a-3b3a3b3a3b3a");

		// when
		FriendlyId fromUuid = FriendlyId.of(uuid);
		FriendlyId fromString = FriendlyId.fromString(com.devskiller.friendly_id.FriendlyId.toFriendlyId(uuid));

		// then
		assertEquals(fromUuid, fromString);
		assertEquals(fromUuid.hashCode(), fromString.hashCode());
		assertEquals(fromUuid.toString(), fromString.toString());
	}
}
