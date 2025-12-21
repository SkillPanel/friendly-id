package com.devskiller.friendly_id.jackson2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * JSON deserializer for {@link FriendlyId} value object.
 * Deserializes JSON strings to FriendlyId instances.
 */
public class FriendlyIdValueDeserializer extends JsonDeserializer<FriendlyId> {

	@Override
	public FriendlyId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String friendlyIdString = p.getValueAsString();
		return FriendlyId.fromString(friendlyIdString);
	}
}
