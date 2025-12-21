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
	 * Converts a string to UUID, accepting both UUID and FriendlyId formats.
	 * <p>
	 * This method auto-detects the format:
	 * <ul>
	 *   <li>Standard UUID format (36 chars with hyphens): parsed directly</li>
	 *   <li>FriendlyId format (up to 22 chars): decoded from Base62</li>
	 * </ul>
	 *
	 * @param value UUID or FriendlyId string
	 * @return parsed UUID
	 * @throws IllegalArgumentException if value is not a valid UUID or FriendlyId
	 */
	public static UUID toUuid(String value) {
		if (isStandardUuidFormat(value)) {
			return UUID.fromString(value);
		}
		return Url62.decode(value);
	}

	private static boolean isStandardUuidFormat(String value) {
		return value.length() == 36
				&& value.charAt(8) == '-'
				&& value.charAt(13) == '-'
				&& value.charAt(18) == '-'
				&& value.charAt(23) == '-';
	}

}
