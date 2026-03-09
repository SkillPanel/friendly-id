package com.devskiller.friendly_id.jackson2;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import com.devskiller.friendly_id.FriendlyIds;

public class FriendlyIdSerializer extends StdSerializer<UUID> {

	private final boolean useFriendlyFormat;

	public FriendlyIdSerializer() {
		this(true);
	}

	FriendlyIdSerializer(boolean useFriendlyFormat) {
		super(UUID.class);
		this.useFriendlyFormat = useFriendlyFormat;
	}

	@Override
	public void serialize(UUID uuid, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (useFriendlyFormat) {
			jsonGenerator.writeString(FriendlyIds.toFriendlyId(uuid));
		} else {
			jsonGenerator.writeString(uuid.toString());
		}
	}
}
