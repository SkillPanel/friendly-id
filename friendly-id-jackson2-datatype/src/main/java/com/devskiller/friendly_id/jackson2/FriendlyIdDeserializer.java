package com.devskiller.friendly_id.jackson2;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;

import com.devskiller.friendly_id.FriendlyIds;

public class FriendlyIdDeserializer extends UUIDDeserializer {

	@Override
	public UUID deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
		JsonToken token = parser.getCurrentToken();
		if (token == JsonToken.VALUE_STRING) {
			String value = parser.getValueAsString().trim();
			return FriendlyIds.toUuid(value);
		}
		throw new IllegalStateException("Expected UUID or FriendlyId string");
	}
}
