package com.devskiller.friendly_id.jooq;

import java.util.UUID;

import org.jooq.Converter;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * jOOQ converter for transparent UUID to FriendlyId conversion in database queries.
 * <p>
 * This converter allows you to work with FriendlyId value objects in your Java code while
 * storing UUIDs in the database. jOOQ will automatically handle the conversion between
 * the two representations.
 * </p>
 * <p>
 * The FriendlyId value object is memory-efficient, storing the UUID internally (16 bytes)
 * and computing the FriendlyId string representation only when needed (e.g., toString()).
 * This is more efficient than storing String representations (~40-50 bytes).
 * </p>
 *
 * <h2>Usage with jOOQ Code Generator</h2>
 * <p>
 * Configure this converter in your jOOQ code generation configuration to apply it
 * to specific columns or all UUID columns:
 * </p>
 * <pre>{@code
 * <configuration>
 *   <generator>
 *     <database>
 *       <forcedTypes>
 *         <forcedType>
 *           <userType>com.devskiller.friendly_id.type.FriendlyId</userType>
 *           <converter>com.devskiller.friendly_id.jooq.FriendlyIdConverter</converter>
 *           <includeExpression>.*\.ID</includeExpression>
 *           <includeTypes>UUID</includeTypes>
 *         </forcedType>
 *       </forcedTypes>
 *     </database>
 *   </generator>
 * </configuration>
 * }</pre>
 *
 * <h2>Manual Usage Example</h2>
 * <pre>{@code
 * // Query using FriendlyId
 * FriendlyId friendlyId = FriendlyId.parse("5wbwf6yUxVBcr48AMbz9cb");
 * UserRecord user = create
 *     .selectFrom(USER)
 *     .where(USER.ID.eq(friendlyId))
 *     .fetchOne();
 *
 * // Get FriendlyId from result
 * FriendlyId userId = user.getId(); // Returns FriendlyId value object
 * String friendlyIdString = userId.toString(); // Get string when needed
 *
 * // Insert with FriendlyId
 * create.insertInto(USER)
 *     .set(USER.ID, FriendlyId.random())
 *     .set(USER.NAME, "John Doe")
 *     .execute();
 * }</pre>
 *
 * @see FriendlyId
 * @see org.jooq.Converter
 */
public class FriendlyIdConverter implements Converter<UUID, FriendlyId> {

	private static final long serialVersionUID = 1L;

	/**
	 * Converts a database UUID to a FriendlyId value object.
	 *
	 * @param databaseObject the UUID from the database, may be {@code null}
	 * @return the FriendlyId value object, or {@code null} if input is {@code null}
	 */
	@Override
	public FriendlyId from(UUID databaseObject) {
		return databaseObject == null ? null : FriendlyId.of(databaseObject);
	}

	/**
	 * Converts a FriendlyId value object to a database UUID.
	 *
	 * @param userObject the FriendlyId value object, may be {@code null}
	 * @return the UUID representation, or {@code null} if input is {@code null}
	 */
	@Override
	public UUID to(FriendlyId userObject) {
		return userObject == null ? null : userObject.uuid();
	}

	/**
	 * Returns the database type (UUID).
	 *
	 * @return {@code UUID.class}
	 */
	@Override
	public Class<UUID> fromType() {
		return UUID.class;
	}

	/**
	 * Returns the user type (FriendlyId).
	 *
	 * @return {@code FriendlyId.class}
	 */
	@Override
	public Class<FriendlyId> toType() {
		return FriendlyId.class;
	}
}
