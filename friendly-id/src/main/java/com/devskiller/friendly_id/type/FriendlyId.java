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
 * FriendlyId id = FriendlyId.fromString("5wbwf6yUxVBcr48AMbz9cb");
 *
 * // Create random
 * FriendlyId id = FriendlyId.random();
 *
 * // Get UUID
 * UUID uuid = id.uuid();
 *
 * // Get FriendlyId string (computed on demand)
 * String friendlyIdString = id.toString();
 * }</pre>
 *
 * @since 1.1.1
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
	 * Creates a FriendlyId from a FriendlyId string representation.
	 *
	 * @param friendlyId the FriendlyId string to decode, must not be null
	 * @return a new FriendlyId instance
	 * @throws NullPointerException if friendlyId is null
	 * @throws IllegalArgumentException if friendlyId is not a valid FriendlyId
	 */
	public static FriendlyId fromString(String friendlyId) {
		Objects.requireNonNull(friendlyId, "FriendlyId string cannot be null");
		return new FriendlyId(com.devskiller.friendly_id.FriendlyId.toUuid(friendlyId));
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
	public UUID uuid() {
		return uuid;
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
		return com.devskiller.friendly_id.FriendlyId.toFriendlyId(uuid);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FriendlyId that = (FriendlyId) o;
		return uuid.equals(that.uuid);
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
