package com.devskiller.friendly_id.openfeign;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.http.converter.autoconfigure.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

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
@SuppressWarnings("deprecation")
public class FriendlyIdConfiguration {

	/**
	 * Creates a FriendlyId-aware Feign encoder.
	 * The encoder delegates to SpringEncoder for actual encoding but intercepts
	 * UUID and FriendlyId objects to convert them to FriendlyId strings.
	 */
	@Bean
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Encoder feignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
		// Cast needed due to API compatibility between Spring Boot 4 and Spring Cloud OpenFeign
		return new FriendlyIdEncoder(new SpringEncoder((ObjectFactory) messageConverters));
	}

	/**
	 * Creates a FriendlyId-aware Feign decoder.
	 * The decoder delegates to SpringDecoder for actual decoding but converts
	 * FriendlyId strings back to UUID or FriendlyId objects when needed.
	 */
	@Bean
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
		// Cast needed due to API compatibility between Spring Boot 4 and Spring Cloud OpenFeign
		return new FriendlyIdDecoder(new SpringDecoder((ObjectFactory) messageConverters));
	}
}
