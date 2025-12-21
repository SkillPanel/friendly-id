package com.devskiller.friendly_id.type;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value object representing a FriendlyId that wraps a UUID.
 * <p>
 * This class provides a memory-efficient way to work with FriendlyIds in your domain model
 * while maintaining the compact UUID representation internally. The FriendlyId string
 * representation is only computed when needed (e.g., for serialization or toString()).
 * </p>
 * <p>
 * This type is designed to be used with:
 * </p>
 * <ul>
 *   <li>jOOQ converters for database mapping</li>
 *   <li>JPA AttributeConverters for entity mapping</li>
 *   <li>Jackson serializers/deserializers for JSON</li>
 *   <li>Spring MVC converters for request parameters</li>
 * </ul>
 *
 * <h2>Memory Efficiency</h2>
 * <p>
 * Storing FriendlyId as a value object with UUID internally is more memory-efficient
 * than storing the String representation:
 * </p>
 * <ul>
 *   <li>UUID: 16 bytes</li>
 *   <li>FriendlyId object: ~28 bytes (16 bytes UUID + ~12 bytes object header)</li>
 *   <li>String: ~40-50 bytes (depending on FriendlyId length)</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * // Create from UUID
 * UUID uuid = UUID.randomUUID();
 * FriendlyId id = FriendlyId.of(uuid);
 *
 * // Create from String
 * FriendlyId id = FriendlyId.parse("5wbwf6yUxVBcr48AMbz9cb");
 *
 * // Create random
 * FriendlyId id = FriendlyId.random();
 *
 * // Static import friendly
 * import static com.devskiller.friendly_id.type.FriendlyId.friendlyId;
 * FriendlyId id = friendlyId(uuid);
 *
 * // Get UUID
 * UUID uuid = id.toUuid();
 *
 * // Get FriendlyId string
 * String friendlyIdString = id.value();
 * }</pre>
 *
 * @since 2.0
 */
public final class FriendlyId implements Serializable, Comparable<FriendlyId> {

	private static final long serialVersionUID = 1L;

	private final UUID uuid;

	private FriendlyId(UUID uuid) {
		this.uuid = Objects.requireNonNull(uuid, "UUID cannot be null");
	}

	/**
	 * Creates a FriendlyId from a UUID.
	 *
	 * @param uuid the UUID to wrap, must not be null
	 * @return a new FriendlyId instance
	 * @throws NullPointerException if uuid is null
	 */
	public static FriendlyId of(UUID uuid) {
		return new FriendlyId(uuid);
	}

	/**
	 * Creates a FriendlyId from a UUID.
	 * <p>
	 * This method is designed for static imports:
	 * <pre>{@code
	 * import static com.devskiller.friendly_id.type.FriendlyId.friendlyId;
	 * FriendlyId id = friendlyId(uuid);
	 * }</pre>
	 *
	 * @param uuid the UUID to wrap, must not be null
	 * @return a new FriendlyId instance
	 * @throws NullPointerException if uuid is null
	 */
	public static FriendlyId friendlyId(UUID uuid) {
		return new FriendlyId(uuid);
	}

	/**
	 * Parses a string representation to FriendlyId.
	 * <p>
	 * Accepts both FriendlyId format (e.g., "5wbwf6yUxVBcr48AMbz9cb") and
	 * standard UUID format (e.g., "c3587ec5-0976-497f-8374-61e0c2ea3da5").
	 *
	 * @param value the FriendlyId or UUID string to parse, must not be null
	 * @return a new FriendlyId instance
	 * @throws NullPointerException if value is null
	 * @throws IllegalArgumentException if value is not a valid FriendlyId or UUID
	 */
	public static FriendlyId parse(String value) {
		Objects.requireNonNull(value, "Value cannot be null");
		if (isStandardUuidFormat(value)) {
			return new FriendlyId(UUID.fromString(value));
		}
		return new FriendlyId(com.devskiller.friendly_id.FriendlyId.toUuid(value));
	}

	/**
	 * Checks if the string matches standard UUID format (36 chars with hyphens).
	 */
	private static boolean isStandardUuidFormat(String value) {
		return value.length() == 36
				&& value.charAt(8) == '-'
				&& value.charAt(13) == '-'
				&& value.charAt(18) == '-'
				&& value.charAt(23) == '-';
	}

	/**
	 * Creates a random FriendlyId.
	 *
	 * @return a new random FriendlyId instance
	 */
	public static FriendlyId random() {
		return new FriendlyId(UUID.randomUUID());
	}

	/**
	 * Returns the underlying UUID.
	 *
	 * @return the UUID
	 */
	public UUID toUuid() {
		return uuid;
	}

	/**
	 * Returns the underlying UUID.
	 * <p>
	 * This is an alias for {@link #toUuid()} following record-style naming.
	 *
	 * @return the UUID
	 */
	public UUID uuid() {
		return uuid;
	}

	/**
	 * Returns the FriendlyId string representation.
	 * <p>
	 * The string is computed on demand from the internal UUID.
	 *
	 * @return the FriendlyId string
	 */
	public String value() {
		return com.devskiller.friendly_id.FriendlyId.toFriendlyId(uuid);
	}

	/**
	 * Returns the FriendlyId string representation.
	 * <p>
	 * The string is computed on demand from the internal UUID.
	 * </p>
	 *
	 * @return the FriendlyId string
	 */
	@Override
	public String toString() {
		return value();
	}

	@Override
	public boolean equals(Object o) {
		return this == o || (o instanceof FriendlyId that && uuid.equals(that.uuid));
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public int compareTo(FriendlyId other) {
		return this.uuid.compareTo(other.uuid);
	}
}
