package com.devskiller.friendly_id.jooq;

import java.util.UUID;

import org.jooq.Converter;

import com.devskiller.friendly_id.FriendlyId;

/**
 * jOOQ converter for transparent UUID to FriendlyId conversion in database queries.
 * <p>
 * This converter allows you to work with FriendlyId strings in your Java code while
 * storing UUIDs in the database. jOOQ will automatically handle the conversion between
 * the two representations.
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
 *           <userType>java.lang.String</userType>
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
 * String friendlyId = "cafe";
 * UserRecord user = create
 *     .selectFrom(USER)
 *     .where(USER.ID.eq(friendlyId))
 *     .fetchOne();
 *
 * // Get FriendlyId from result
 * String userId = user.getId(); // Returns FriendlyId string, not UUID
 *
 * // Insert with FriendlyId
 * create.insertInto(USER)
 *     .set(USER.ID, "newUserId")
 *     .set(USER.NAME, "John Doe")
 *     .execute();
 * }</pre>
 *
 * @see FriendlyId
 * @see org.jooq.Converter
 */
public class FriendlyIdConverter implements Converter<UUID, String> {

	private static final long serialVersionUID = 1L;

	/**
	 * Converts a database UUID to a FriendlyId string.
	 *
	 * @param databaseObject the UUID from the database, may be {@code null}
	 * @return the FriendlyId string representation, or {@code null} if input is {@code null}
	 */
	@Override
	public String from(UUID databaseObject) {
		return databaseObject == null ? null : FriendlyId.toFriendlyId(databaseObject);
	}

	/**
	 * Converts a FriendlyId string to a database UUID.
	 *
	 * @param userObject the FriendlyId string, may be {@code null}
	 * @return the UUID representation, or {@code null} if input is {@code null}
	 */
	@Override
	public UUID to(String userObject) {
		return userObject == null ? null : FriendlyId.toUuid(userObject);
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
	 * Returns the user type (String).
	 *
	 * @return {@code String.class}
	 */
	@Override
	public Class<String> toType() {
		return String.class;
	}
}
