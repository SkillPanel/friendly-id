package com.devskiller.friendly_id;

import java.util.UUID;

/**
 * Utility class to convert between UUID and FriendlyId strings.
 * <p>
 * FriendlyId is a URL-friendly Base62 encoding of UUID that produces
 * shorter strings (up to 22 characters) compared to standard UUID format (36 characters).
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * // Create random FriendlyId
 * String id = FriendlyIds.createFriendlyId();
 *
 * // Convert UUID to FriendlyId
 * String friendlyId = FriendlyIds.toFriendlyId(uuid);
 *
 * // Convert FriendlyId to UUID
 * UUID uuid = FriendlyIds.toUuid(friendlyId);
 * }</pre>
 *
 * @since 2.0
 * @see com.devskiller.friendly_id.type.FriendlyId
 */
public final class FriendlyIds {

	private FriendlyIds() {
		// utility class
	}

	/**
	 * Creates a random FriendlyId string.
	 *
	 * @return FriendlyId encoded random UUID
	 */
	public static String createFriendlyId() {
		return Url62.encode(UUID.randomUUID());
	}

	/**
	 * Encodes a UUID to FriendlyId string.
	 *
	 * @param uuid UUID to be encoded
	 * @return FriendlyId encoded UUID
	 */
	public static String toFriendlyId(UUID uuid) {
		return Url62.encode(uuid);
	}

	/**
	 * Decodes a FriendlyId string to UUID.
	 *
	 * @param friendlyId encoded UUID
	 * @return decoded UUID
	 */
	public static UUID toUuid(String friendlyId) {
		return Url62.decode(friendlyId);
	}

}
