package com.devskiller.friendly_id.spring;

import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.json.JsonMapper;

import com.devskiller.friendly_id.jackson.FriendlyIdModule;

public class ObjectMapperConfiguration {

	protected static JsonMapper mapper(JacksonModule... modules) {
		JsonMapper.Builder builder = JsonMapper.builder()
				.addModule(new FriendlyIdModule());
		for (JacksonModule module : modules) {
			builder.addModule(module);
		}
		return builder.build();
	}
}
