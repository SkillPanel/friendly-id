package com.devskiller.friendly_id.sample.jpa;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.devskiller.friendly_id.type.FriendlyId;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test demonstrating FriendlyId usage with JPA.
 * <p>
 * This test shows that FriendlyId works seamlessly across the entire stack:
 * </p>
 * <ol>
 *   <li>Entity stored in database with FriendlyId as UUID</li>
 *   <li>REST controller accepts FriendlyId in @PathVariable</li>
 *   <li>JSON serialization converts FriendlyId to/from string</li>
 * </ol>
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProductClientIntegrationTest {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private MockMvc mockMvc;

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
	void shouldRetrieveAllProducts() throws Exception {
		mockMvc.perform(get("/api/products")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is("Test Product")));
	}

	@Test
	void shouldRetrieveProductByFriendlyId() throws Exception {
		FriendlyId productId = testProduct.getId();

		mockMvc.perform(get("/api/products/{id}", productId.toString())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(productId.toString())))
				.andExpect(jsonPath("$.name", is("Test Product")))
				.andExpect(jsonPath("$.description", is("Product for integration testing")))
				.andExpect(jsonPath("$.price", is(99.99)))
				.andExpect(jsonPath("$.stock", is(10)));
	}

	@Test
	void shouldHandleFriendlyIdConversionInUrlPath() throws Exception {
		// This test verifies that FriendlyId in @PathVariable is correctly:
		// 1. Parsed from string by Spring MVC converter
		// 2. Used to query the database via JPA converter
		// 3. Serialized to JSON string in response

		mockMvc.perform(get("/api/products/{id}", testProduct.getId().toString())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", matchesPattern("[0-9A-Za-z]{21,22}")));
	}
}
