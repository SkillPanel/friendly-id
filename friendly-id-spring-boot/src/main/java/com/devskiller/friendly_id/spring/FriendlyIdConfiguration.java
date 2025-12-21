package com.devskiller.friendly_id.spring;

import java.util.UUID;

import tools.jackson.databind.JacksonModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.devskiller.friendly_id.jackson.FriendlyIdModule;

import static com.devskiller.friendly_id.FriendlyId.toFriendlyId;
import static com.devskiller.friendly_id.FriendlyId.toUuid;

/**
 * Configuration for FriendlyId integration with Spring MVC.
 * <p>
 * This configuration:
 * <ul>
 *   <li>Registers converters for automatic String ⇄ UUID conversion in path variables and request parameters</li>
 *   <li>Registers Jackson module for JSON serialization/deserialization of UUIDs as FriendlyIds</li>
 * </ul>
 * <p>
 * Enable this configuration by adding {@link EnableFriendlyId @EnableFriendlyId} to your configuration class,
 * or use the spring-boot-starter for automatic configuration.
 */
@Configuration
public class FriendlyIdConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(String.class, UUID.class, id -> toUuid(id));
		registry.addConverter(UUID.class, String.class, id -> toFriendlyId(id));
		registry.addConverter(String.class, com.devskiller.friendly_id.type.FriendlyId.class,
				com.devskiller.friendly_id.type.FriendlyId::fromString);
		registry.addConverter(com.devskiller.friendly_id.type.FriendlyId.class, String.class,
				com.devskiller.friendly_id.type.FriendlyId::toString);
	}

	@Bean
	public JacksonModule friendlyIdModule() {
		return new FriendlyIdModule();
	}
}
