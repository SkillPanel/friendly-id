package com.devskiller.friendly_id.jackson2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * JSON serializer for {@link FriendlyId} value object.
 * Serializes FriendlyId instances as their string representation.
 */
public class FriendlyIdValueSerializer extends JsonSerializer<FriendlyId> {

	@Override
	public void serialize(FriendlyId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.toString());
	}
}
