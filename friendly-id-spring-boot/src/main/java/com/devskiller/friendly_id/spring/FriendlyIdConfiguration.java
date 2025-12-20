package com.devskiller.friendly_id.spring;

import java.util.UUID;

import tools.jackson.databind.JacksonModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.devskiller.friendly_id.FriendlyId;
import com.devskiller.friendly_id.jackson.FriendlyIdModule;

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
		registry.addConverter(new StringToUuidConverter());
		registry.addConverter(new UuidToStringConverter());
		registry.addConverter(new StringToFriendlyIdConverter());
		registry.addConverter(new FriendlyIdToStringConverter());
	}

	@Bean
	public JacksonModule friendlyIdModule() {
		return new FriendlyIdModule();
	}

	/**
	 * Converter that converts FriendlyId strings to UUID.
	 * <p>
	 * This converter is automatically registered in Spring's conversion service
	 * and allows path variables and request parameters to be automatically converted
	 * from FriendlyId format to UUID.
	 */
	public static class StringToUuidConverter implements Converter<String, UUID> {

		@Override
		public UUID convert(String id) {
			return FriendlyId.toUuid(id);
		}
	}

	/**
	 * Converter that converts UUID to FriendlyId strings.
	 * <p>
	 * This converter is automatically registered in Spring's conversion service
	 * and allows UUIDs to be automatically converted to FriendlyId format
	 * in responses and URL generation.
	 */
	public static class UuidToStringConverter implements Converter<UUID, String> {

		@Override
		public String convert(UUID id) {
			return FriendlyId.toFriendlyId(id);
		}
	}

	/**
	 * Converter that converts FriendlyId strings to FriendlyId value objects.
	 * <p>
	 * This converter is automatically registered in Spring's conversion service
	 * and allows path variables and request parameters to be automatically converted
	 * from FriendlyId format to FriendlyId value object.
	 */
	public static class StringToFriendlyIdConverter implements Converter<String, com.devskiller.friendly_id.type.FriendlyId> {

		@Override
		public com.devskiller.friendly_id.type.FriendlyId convert(String id) {
			return com.devskiller.friendly_id.type.FriendlyId.fromString(id);
		}
	}

	/**
	 * Converter that converts FriendlyId value objects to FriendlyId strings.
	 * <p>
	 * This converter is automatically registered in Spring's conversion service
	 * and allows FriendlyId value objects to be automatically converted to FriendlyId format
	 * in responses and URL generation.
	 */
	public static class FriendlyIdToStringConverter implements Converter<com.devskiller.friendly_id.type.FriendlyId, String> {

		@Override
		public String convert(com.devskiller.friendly_id.type.FriendlyId id) {
			return id.toString();
		}
	}
}
