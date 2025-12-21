package com.devskiller.friendly_id.sample.jpa;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * REST controller demonstrating FriendlyId usage with Spring MVC and JPA.
 * <p>
 * Key features demonstrated:
 * </p>
 * <ul>
 *   <li>@PathVariable automatically converts FriendlyId string to FriendlyId value object</li>
 *   <li>Response JSON contains FriendlyId as Base62 string (thanks to Jackson integration)</li>
 *   <li>Database stores UUID internally (thanks to JPA converter)</li>
 * </ul>
 * <p>
 * Example URLs:
 * </p>
 * <pre>
 * GET  /api/products                    - List all products
 * GET  /api/products/5wbwf6yUxVBcr48   - Get product by FriendlyId
 * POST /api/products                    - Create new product
 * PUT  /api/products/5wbwf6yUxVBcr48   - Update product
 * DELETE /api/products/5wbwf6yUxVBcr48 - Delete product
 * </pre>
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductRepository productRepository;

	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * Get all products.
	 * <p>
	 * Response: JSON array with FriendlyId strings instead of UUIDs.
	 * </p>
	 */
	@GetMapping
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	/**
	 * Get product by FriendlyId.
	 * <p>
	 * Example: GET /api/products/5wbwf6yUxVBcr48AMbz9cb
	 * </p>
	 * <p>
	 * The FriendlyId string from URL is automatically converted to FriendlyId value object
	 * by Spring's StringToFriendlyIdConverter.
	 * </p>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable FriendlyId id) {
		return productRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Create new product.
	 * <p>
	 * Request body should NOT include 'id' - it will be generated automatically.
	 * </p>
	 * <p>
	 * Example request:
	 * </p>
	 * <pre>
	 * {
	 *   "name": "Laptop",
	 *   "description": "High-performance laptop",
	 *   "price": 1299.99,
	 *   "stock": 10
	 * }
	 * </pre>
	 */
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request) {
		Product product = new Product(
				request.name(),
				request.description(),
				request.price(),
				request.stock()
		);
		Product savedProduct = productRepository.save(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}

	/**
	 * Update existing product.
	 * <p>
	 * Example: PUT /api/products/5wbwf6yUxVBcr48AMbz9cb
	 * </p>
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(
			@PathVariable FriendlyId id,
			@RequestBody ProductRequest request) {

		return productRepository.findById(id)
				.map(existingProduct -> {
					existingProduct.setName(request.name());
					existingProduct.setDescription(request.description());
					existingProduct.setPrice(request.price());
					existingProduct.setStock(request.stock());
					Product updatedProduct = productRepository.save(existingProduct);
					return ResponseEntity.ok(updatedProduct);
				})
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Delete product by FriendlyId.
	 * <p>
	 * Example: DELETE /api/products/5wbwf6yUxVBcr48AMbz9cb
	 * </p>
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable FriendlyId id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
