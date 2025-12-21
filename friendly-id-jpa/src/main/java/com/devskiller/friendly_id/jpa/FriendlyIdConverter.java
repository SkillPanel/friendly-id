package com.devskiller.friendly_id.jpa;

import java.util.UUID;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * JPA AttributeConverter for transparent UUID to FriendlyId conversion in entity mappings.
 * <p>
 * This converter allows you to work with FriendlyId value objects in your JPA entities while
 * storing UUIDs in the database. JPA will automatically handle the conversion between
 * the two representations.
 * </p>
 * <p>
 * The FriendlyId value object is memory-efficient, storing the UUID internally (16 bytes)
 * and computing the FriendlyId string representation only when needed (e.g., toString()).
 * This is more efficient than storing String representations (~40-50 bytes).
 * </p>
 *
 * <h2>Automatic Registration (JPA 2.1+)</h2>
 * <p>
 * The converter is automatically applied to all FriendlyId attributes thanks to the
 * {@code @Converter(autoApply = true)} annotation. No additional configuration needed.
 * </p>
 *
 * <h2>Usage in Entity</h2>
 * <pre>{@code
 * @Entity
 * public class User {
 *     @Id
 *     private FriendlyId id;
 *
 *     private String name;
 *
 *     // getters/setters
 * }
 * }</pre>
 *
 * <h2>Query Examples</h2>
 * <pre>{@code
 * // JPQL - use UUID parameter
 * FriendlyId userId = FriendlyId.parse("5wbwf6yUxVBcr48AMbz9cb");
 * User user = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
 *     .setParameter("id", userId)
 *     .getSingleResult();
 *
 * // Criteria API
 * CriteriaBuilder cb = em.getCriteriaBuilder();
 * CriteriaQuery<User> query = cb.createQuery(User.class);
 * Root<User> root = query.from(User.class);
 * query.where(cb.equal(root.get("id"), userId));
 *
 * // Native query - use UUID
 * User user = em.createNativeQuery(
 *         "SELECT * FROM users WHERE id = ?",
 *         User.class)
 *     .setParameter(1, userId.uuid())
 *     .getSingleResult();
 * }</pre>
 *
 * <h2>Manual Application (Optional)</h2>
 * <p>
 * If autoApply is disabled or you need explicit control:
 * </p>
 * <pre>{@code
 * @Entity
 * public class User {
 *     @Id
 *     @Convert(converter = FriendlyIdConverter.class)
 *     private FriendlyId id;
 * }
 * }</pre>
 *
 * @see FriendlyId
 * @see jakarta.persistence.AttributeConverter
 * @since 1.1.1
 */
@Converter(autoApply = true)
public class FriendlyIdConverter implements AttributeConverter<FriendlyId, UUID> {

	/**
	 * Converts a FriendlyId value object to a database UUID.
	 *
	 * @param attribute the FriendlyId value object, may be {@code null}
	 * @return the UUID for database storage, or {@code null} if input is {@code null}
	 */
	@Override
	public UUID convertToDatabaseColumn(FriendlyId attribute) {
		return attribute == null ? null : attribute.uuid();
	}

	/**
	 * Converts a database UUID to a FriendlyId value object.
	 *
	 * @param dbData the UUID from the database, may be {@code null}
	 * @return the FriendlyId value object, or {@code null} if input is {@code null}
	 */
	@Override
	public FriendlyId convertToEntityAttribute(UUID dbData) {
		return dbData == null ? null : FriendlyId.of(dbData);
	}
}
