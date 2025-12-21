package com.devskiller.friendly_id.jackson2;

import java.util.UUID;

import com.devskiller.friendly_id.type.FriendlyId;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class FriendlyIdJackson2Module extends SimpleModule {

	private final FriendlyIdAnnotationIntrospector introspector;

	public FriendlyIdJackson2Module() {
		introspector = new FriendlyIdAnnotationIntrospector();
		addDeserializer(UUID.class, new FriendlyIdDeserializer());
		addSerializer(UUID.class, new FriendlyIdSerializer());

		// Add serializer/deserializer for FriendlyId value object
		addDeserializer(FriendlyId.class, new FriendlyIdValueDeserializer());
		addSerializer(FriendlyId.class, new FriendlyIdValueSerializer());
	}

	@Override
	public void setupModule(SetupContext context) {
		super.setupModule(context);
		context.insertAnnotationIntrospector(introspector);
	}
}
