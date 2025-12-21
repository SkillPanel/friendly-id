package com.devskiller.friendly_id.jackson2;

import java.util.UUID;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class FriendlyIdModule extends SimpleModule {

	private FriendlyIdAnnotationIntrospector introspector;

	public FriendlyIdModule() {
		introspector = new FriendlyIdAnnotationIntrospector();
		addDeserializer(UUID.class, new FriendlyIdDeserializer());
		addSerializer(UUID.class, new FriendlyIdSerializer());

		// Add serializer/deserializer for FriendlyId value object
		addDeserializer(com.devskiller.friendly_id.type.FriendlyId.class, new FriendlyIdValueDeserializer());
		addSerializer(com.devskiller.friendly_id.type.FriendlyId.class, new FriendlyIdValueSerializer());
	}

	@Override
	public void setupModule(SetupContext context) {
		super.setupModule(context);
		context.insertAnnotationIntrospector(introspector);
	}
}
