package com.devskiller.friendly_id.sample.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.devskiller.friendly_id.type.FriendlyId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test demonstrating FriendlyId usage with OpenFeign.
 * <p>
 * This test shows that FriendlyId works seamlessly across the entire stack:
 * </p>
 * <ol>
 *   <li>Entity stored in database with FriendlyId as UUID</li>
 *   <li>REST controller accepts FriendlyId in @PathVariable</li>
 *   <li>JSON serialization converts FriendlyId to/from string</li>
 *   <li>OpenFeign client automatically handles FriendlyId conversion</li>
 * </ol>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients
class ProductClientIntegrationTest {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductClient client;

	private Product testProduct;

	@BeforeEach
	void setUp() {
		repository.deleteAll();
		
		testProduct = new Product(
				"Test Product",
				"Product for integration testing",
				new BigDecimal("99.99"),
				10
		);
		repository.save(testProduct);
	}

	@Test
	void shouldRetrieveAllProductsViaOpenFeign() {
		List<Product> products = client.getAllProducts();

		assertThat(products).hasSize(1);
		assertThat(products.get(0).getId()).isEqualTo(testProduct.getId());
		assertThat(products.get(0).getName()).isEqualTo("Test Product");
	}

	@Test
	void shouldRetrieveProductByFriendlyIdViaOpenFeign() {
		FriendlyId productId = testProduct.getId();

		Product retrievedProduct = client.getProductById(productId);

		assertThat(retrievedProduct).isNotNull();
		assertThat(retrievedProduct.getId()).isEqualTo(productId);
		assertThat(retrievedProduct.getName()).isEqualTo("Test Product");
		assertThat(retrievedProduct.getDescription()).isEqualTo("Product for integration testing");
		assertThat(retrievedProduct.getPrice()).isEqualByComparingTo("99.99");
		assertThat(retrievedProduct.getStock()).isEqualTo(10);
	}

	@Test
	void shouldHandleFriendlyIdConversionInUrlPath() {
		// This test verifies that FriendlyId in @PathVariable is correctly:
		// 1. Converted to string in the URL by OpenFeign encoder
		// 2. Parsed from string by Spring MVC converter
		// 3. Used to query the database
		// 4. Serialized to JSON string in response
		// 5. Deserialized by OpenFeign decoder back to FriendlyId object

		Product product = client.getProductById(testProduct.getId());

		assertThat(product.getId()).isEqualTo(testProduct.getId());
		assertThat(product.getId().toString()).matches("[0-9A-Za-z]{21,22}");
	}
}
