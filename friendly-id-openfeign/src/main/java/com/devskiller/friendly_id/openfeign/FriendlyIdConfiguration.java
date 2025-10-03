package com.devskiller.friendly_id.openfeign;

import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Decoder;
import feign.codec.Encoder;

/**
 * Configuration for FriendlyId integration with Spring Cloud OpenFeign.
 * <p>
 * This configuration can be used with {@code @FeignClient} to enable FriendlyId support:
 * </p>
 * <pre>{@code
 * @FeignClient(name = "user-service", configuration = FriendlyIdConfiguration.class)
 * public interface UserClient {
 *
 *     @GetMapping("/users/{id}")
 *     UserDto getUser(@PathVariable UUID id);
 *
 *     @GetMapping("/users/{id}/profile")
 *     ProfileDto getProfile(@PathVariable FriendlyId id);
 * }
 * }</pre>
 * <p>
 * The configuration registers custom encoder and decoder that:
 * </p>
 * <ul>
 *   <li>Convert UUID/FriendlyId to FriendlyId strings in request URLs/bodies</li>
 *   <li>Convert FriendlyId strings back to UUID/FriendlyId in responses</li>
 * </ul>
 *
 * @since 1.1.1
 */
public class FriendlyIdConfiguration {

	/**
	 * Creates a FriendlyId-aware Feign encoder.
	 * The encoder delegates to SpringEncoder for actual encoding but intercepts
	 * UUID and FriendlyId objects to convert them to FriendlyId strings.
	 */
	@Bean
	public Encoder feignEncoder() {
		return new FriendlyIdEncoder(new SpringEncoder(() -> new HttpMessageConverters()));
	}

	/**
	 * Creates a FriendlyId-aware Feign decoder.
	 * The decoder delegates to SpringDecoder for actual decoding but converts
	 * FriendlyId strings back to UUID or FriendlyId objects when needed.
	 */
	@Bean
	public Decoder feignDecoder() {
		return new FriendlyIdDecoder(new org.springframework.cloud.openfeign.support.SpringDecoder(() -> new HttpMessageConverters()));
	}
}
