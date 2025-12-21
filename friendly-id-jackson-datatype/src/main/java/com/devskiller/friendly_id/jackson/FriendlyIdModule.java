package com.devskiller.friendly_id.jackson;

import java.util.UUID;

import tools.jackson.databind.module.SimpleModule;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * Jackson 3 module for FriendlyId serialization/deserialization.
 * <p>
 * This module registers custom serializers and deserializers for UUID and FriendlyId types,
 * enabling automatic conversion between UUID values and their FriendlyId string representation.
 * </p>
 */
public class FriendlyIdModule extends SimpleModule {

	public FriendlyIdModule() {
		super("FriendlyIdModule");

		// UUID serializers/deserializers
		addSerializer(UUID.class, new FriendlyIdSerializer());
		addDeserializer(UUID.class, new FriendlyIdDeserializer());

		// FriendlyId value object serializers/deserializers
		addSerializer(FriendlyId.class, new FriendlyIdValueSerializer());
		addDeserializer(FriendlyId.class, new FriendlyIdValueDeserializer());
	}
}
