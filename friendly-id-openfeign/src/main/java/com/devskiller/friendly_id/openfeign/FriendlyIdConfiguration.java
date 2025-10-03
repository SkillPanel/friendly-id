package com.devskiller.friendly_id.openfeign;

import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Decoder;
import feign.codec.Encoder;

/**
 * Auto-configuration for FriendlyId integration with Spring Cloud OpenFeign.
 * <p>
 * This configuration automatically registers custom encoder and decoder that handle
 * FriendlyId conversion in Feign clients:
 * </p>
 * <ul>
 *   <li>Encoder: Converts UUID/FriendlyId parameters to FriendlyId strings in requests</li>
 *   <li>Decoder: Converts FriendlyId strings to UUID/FriendlyId in responses</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * @FeignClient(name = "user-service")
 * public interface UserClient {
 *
 *     @GetMapping("/users/{id}")
 *     UserDto getUser(@PathVariable UUID id);
 *
 *     @GetMapping("/users/{id}/profile")
 *     ProfileDto getProfile(@PathVariable com.devskiller.friendly_id.type.FriendlyId id);
 * }
 *
 * // Usage
 * UUID userId = UUID.randomUUID();
 * UserDto user = userClient.getUser(userId); // Sends as FriendlyId string
 * }</pre>
 *
 * @since 1.1.1
 */
@Configuration
@ConditionalOnClass(FeignClient.class)
public class FriendlyIdConfiguration {

	@Bean
	public Encoder friendlyIdEncoder(Encoder defaultEncoder) {
		return new FriendlyIdEncoder(defaultEncoder);
	}

	@Bean
	public Decoder friendlyIdDecoder(Decoder defaultDecoder) {
		return new FriendlyIdDecoder(defaultDecoder);
	}
}
