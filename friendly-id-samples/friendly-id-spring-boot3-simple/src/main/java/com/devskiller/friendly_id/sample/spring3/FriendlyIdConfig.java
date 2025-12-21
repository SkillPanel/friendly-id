package com.devskiller.friendly_id.sample.spring3;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.devskiller.friendly_id.FriendlyId;
import com.devskiller.friendly_id.jackson2.FriendlyIdJackson2Module;

@Configuration
public class FriendlyIdConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToUuidConverter());
		registry.addConverter(new UuidToStringConverter());
	}

	@Bean
	FriendlyIdJackson2Module friendlyIdModule() {
		return new FriendlyIdJackson2Module();
	}

	public static class StringToUuidConverter implements Converter<String, UUID> {

		@Override
		public UUID convert(String id) {
			return FriendlyId.toUuid(id);
		}
	}

	public static class UuidToStringConverter implements Converter<UUID, String> {

		@Override
		public String convert(UUID id) {
			return FriendlyId.toFriendlyId(id);
		}
	}
}
