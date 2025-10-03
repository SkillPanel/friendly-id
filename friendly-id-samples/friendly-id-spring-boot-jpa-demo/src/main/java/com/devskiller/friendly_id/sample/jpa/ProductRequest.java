package com.devskiller.friendly_id.sample.jpa;

import java.math.BigDecimal;

/**
 * DTO for creating/updating products.
 */
public record ProductRequest(
		String name,
		String description,
		BigDecimal price,
		Integer stock
) {
}
