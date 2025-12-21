package com.devskiller.friendly_id.sample.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * Spring Data JPA repository for Product entities.
 * <p>
 * Note: The repository uses FriendlyId as the ID type, not UUID.
 * Spring Data automatically handles the FriendlyId type.
 * </p>
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, FriendlyId> {

	/**
	 * Find product by name (case-insensitive).
	 */
	Optional<Product> findByNameIgnoreCase(String name);
}
