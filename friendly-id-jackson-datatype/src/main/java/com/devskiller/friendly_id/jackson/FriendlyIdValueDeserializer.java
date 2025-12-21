package com.devskiller.friendly_id.jackson;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * JSON deserializer for {@link FriendlyId} value object.
 * Deserializes JSON strings to FriendlyId instances.
 */
public class FriendlyIdValueDeserializer extends StdDeserializer<FriendlyId> {

	public FriendlyIdValueDeserializer() {
		super(FriendlyId.class);
	}

	@Override
	public FriendlyId deserialize(JsonParser p, DeserializationContext ctxt) {
		String friendlyIdString = p.getString();
		return FriendlyId.fromString(friendlyIdString);
	}
}
