package com.devskiller.friendly_id.sample.jpa;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot application demonstrating FriendlyId usage with JPA.
 * <p>
 * This demo shows:
 * </p>
 * <ul>
 *   <li>Using FriendlyId as entity ID (stored as UUID in database)</li>
 *   <li>Automatic conversion in REST endpoints via @PathVariable</li>
 *   <li>JSON serialization with FriendlyId strings</li>
 *   <li>H2 console for database inspection</li>
 * </ul>
 * <p>
 * Access points:
 * </p>
 * <ul>
 *   <li>REST API: http://localhost:8080/api/products</li>
 *   <li>H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:friendlyid_demo)</li>
 * </ul>
 */
@SpringBootApplication
public class FriendlyIdJpaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendlyIdJpaDemoApplication.class, args);
	}

	/**
	 * Initialize database with sample data.
	 */
	@Bean
	public CommandLineRunner initData(ProductRepository repository) {
		return args -> {
			System.out.println("\n========================================");
			System.out.println("Initializing demo products...");
			System.out.println("========================================\n");

			Product laptop = new Product(
					"Laptop",
					"High-performance laptop for developers",
					new BigDecimal("1299.99"),
					15
			);
			repository.save(laptop);
			System.out.println("Created product: Laptop with ID: " + laptop.getId());

			Product mouse = new Product(
					"Wireless Mouse",
					"Ergonomic wireless mouse",
					new BigDecimal("29.99"),
					50
			);
			repository.save(mouse);
			System.out.println("Created product: Wireless Mouse with ID: " + mouse.getId());

			Product keyboard = new Product(
					"Mechanical Keyboard",
					"RGB mechanical keyboard with Cherry MX switches",
					new BigDecimal("149.99"),
					25
			);
			repository.save(keyboard);
			System.out.println("Created product: Mechanical Keyboard with ID: " + keyboard.getId());

			System.out.println("\n========================================");
			System.out.println("Demo ready!");
			System.out.println("========================================");
			System.out.println("REST API: http://localhost:8080/api/products");
			System.out.println("H2 Console: http://localhost:8080/h2-console");
			System.out.println("  JDBC URL: jdbc:h2:mem:friendlyid_demo");
			System.out.println("  Username: sa");
			System.out.println("  Password: (empty)");
			System.out.println("========================================\n");

			System.out.println("Try these commands:");
			System.out.println("  curl http://localhost:8080/api/products");
			System.out.println("  curl http://localhost:8080/api/products/" + laptop.getId());
			System.out.println("\n");
		};
	}
}
