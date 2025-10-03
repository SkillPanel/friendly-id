package com.devskiller.friendly_id.sample.jpa;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * OpenFeign client for testing FriendlyId integration.
 * <p>
 * Demonstrates that FriendlyId works seamlessly with OpenFeign:
 * </p>
 * <ul>
 *   <li>@PathVariable FriendlyId is automatically converted to FriendlyId string in URL</li>
 *   <li>Response JSON with FriendlyId strings is automatically deserialized to FriendlyId objects</li>
 * </ul>
 */
@FeignClient(name = "productClient", url = "http://localhost:${server.port}", configuration = com.devskiller.friendly_id.openfeign.FriendlyIdConfiguration.class)
public interface ProductClient {

	@GetMapping("/api/products")
	List<Product> getAllProducts();

	@GetMapping("/api/products/{id}")
	Product getProductById(@PathVariable FriendlyId id);
}
