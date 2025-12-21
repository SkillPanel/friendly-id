package com.devskiller.friendly_id.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * JSON serializer for {@link FriendlyId} value object.
 * Serializes FriendlyId instances as their string representation.
 */
public class FriendlyIdValueSerializer extends StdSerializer<FriendlyId> {

	public FriendlyIdValueSerializer() {
		super(FriendlyId.class);
	}

	@Override
	public void serialize(FriendlyId value, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeString(value.toString());
	}
}
