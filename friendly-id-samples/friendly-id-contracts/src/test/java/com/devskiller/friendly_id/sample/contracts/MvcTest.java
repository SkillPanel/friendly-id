package com.devskiller.friendly_id.sample.contracts;

import com.devskiller.friendly_id.FriendlyId;
import com.devskiller.friendly_id.jackson.FriendlyIdModule;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MvcTest {

	protected StandaloneMockMvcBuilder mockMvcBuilder;

	@BeforeEach
	public void setup() {
		mockMvcBuilder = standaloneSetup(new FooController(mock(EntityLinks.class)));
		DefaultFormattingConversionService service = new DefaultFormattingConversionService();
		service.addConverter(new StringToUuidConverter());
		mockMvcBuilder.setMessageConverters(jackson2HttpMessageConverter()).setConversionService(service);
		RestAssuredMockMvc.standaloneSetup(mockMvcBuilder);
	}

	public static class StringToUuidConverter implements Converter<String, UUID> {

		@Override
		public UUID convert(String id) {
			return FriendlyId.toUuid(id);
		}
	}

	private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		Jackson2ObjectMapperBuilder builder = this.jacksonBuilder();
		converter.setObjectMapper(builder.build());
		return converter;
	}

	protected Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.modules(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES), new JavaTimeModule(), new FriendlyIdModule());
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		builder.simpleDateFormat("yyyy-MM-dd");
		builder.indentOutput(true);
		return builder;
	}


}
