package com.devskiller.friendly_id.sample.contracts;

import com.devskiller.friendly_id.FriendlyId;
import com.devskiller.friendly_id.jackson.FriendlyIdModule;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import tools.jackson.databind.json.JsonMapper;

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
		mockMvcBuilder.setMessageConverters(jacksonHttpMessageConverter()).setConversionService(service);
		RestAssuredMockMvc.standaloneSetup(mockMvcBuilder);
	}

	public static class StringToUuidConverter implements Converter<String, UUID> {

		@Override
		public UUID convert(String id) {
			return FriendlyId.toUuid(id);
		}
	}

	private JacksonJsonHttpMessageConverter jacksonHttpMessageConverter() {
		JsonMapper mapper = jsonMapper();
		return new JacksonJsonHttpMessageConverter(mapper);
	}

	protected JsonMapper jsonMapper() {
		return JsonMapper.builder()
				.addModule(new FriendlyIdModule())
				.build();
	}
}
