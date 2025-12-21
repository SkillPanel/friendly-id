package com.devskiller.friendly_id.spring;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.devskiller.friendly_id.jackson2.FriendlyIdJackson2Module;

public class ObjectMapperConfiguration {

	protected static ObjectMapper mapper(Module... modules) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new FriendlyIdJackson2Module());
		mapper.registerModules(modules);
		return mapper;
	}
}
