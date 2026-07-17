package com.devskiller.friendly_id;

import java.util.Objects;
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

	private static volatile FriendlyIdEncoding encoding = FriendlyIdEncoding.STANDARD;

	private FriendlyIds() {
		// utility class
	}

	/**
	 * Sets the global {@link FriendlyIdEncoding} used by all conversions in this library
	 * (including the Jackson, JPA, jOOQ, OpenFeign and Spring integrations).
	 * <p>
	 * Intended to be called once during application startup, before any conversion happens.
	 * Identifiers encoded with one strategy silently decode to a <em>different</em> UUID
	 * under the other, so switching at runtime on live traffic is not supported.
	 * <p>
	 * With the Spring Boot starter this can be set declaratively via the
	 * {@code com.devskiller.friendly-id.encoding} property.
	 *
	 * @param friendlyIdEncoding encoding to use, must not be null
	 * @throws NullPointerException if friendlyIdEncoding is null
	 * @since 2.0.0-beta6
	 */
	public static void setEncoding(FriendlyIdEncoding friendlyIdEncoding) {
		Objects.requireNonNull(friendlyIdEncoding, "Encoding cannot be null");
		encoding = friendlyIdEncoding;
	}

	/**
	 * Returns the global {@link FriendlyIdEncoding}, {@link FriendlyIdEncoding#STANDARD} by default.
	 *
	 * @return the encoding used by all conversions in this library
	 * @since 2.0.0-beta6
	 */
	public static FriendlyIdEncoding getEncoding() {
		return encoding;
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
	 * @param uuid UUID to be encoded, must not be null
	 * @return FriendlyId encoded UUID
	 * @throws NullPointerException if uuid is null
	 */
	public static String toFriendlyId(UUID uuid) {
		Objects.requireNonNull(uuid, "UUID cannot be null");
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
	 * @param value UUID or FriendlyId string, must not be null
	 * @return parsed UUID
	 * @throws NullPointerException if value is null
	 * @throws IllegalArgumentException if value is not a valid UUID or FriendlyId
	 */
	public static UUID toUuid(String value) {
		Objects.requireNonNull(value, "Value cannot be null");
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
