/**
 * JPA integration for FriendlyId.
 * <p>
 * This package provides JPA AttributeConverter for automatic conversion between
 * FriendlyId value objects and UUID database columns.
 * </p>
 * <p>
 * The converter is automatically applied to all FriendlyId attributes in JPA entities
 * thanks to {@code @Converter(autoApply = true)}.
 * </p>
 *
 * @see com.devskiller.friendly_id.jpa.FriendlyIdConverter
 * @since 1.1.1
 */
package com.devskiller.friendly_id.jpa;
