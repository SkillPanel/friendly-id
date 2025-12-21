package com.devskiller.friendly_id.jackson;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import com.devskiller.friendly_id.FriendlyId;

public class FriendlyIdSerializer extends StdSerializer<UUID> {

	public FriendlyIdSerializer() {
		super(UUID.class);
	}

	@Override
	public void serialize(UUID uuid, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(FriendlyId.toFriendlyId(uuid));
	}
}
