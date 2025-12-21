package com.devskiller.friendly_id.jackson;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;

import com.devskiller.friendly_id.FriendlyId;

public class FriendlyIdDeserializer extends UUIDDeserializer {

	@Override
	public UUID deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {

		JsonToken token = parser.getCurrentToken();
		if (token == JsonToken.VALUE_STRING) {
			String string = parser.getValueAsString().trim();
			if (looksLikeUuid(string)) {
				return super.deserialize(parser, deserializationContext);
			} else {
				return FriendlyId.toUuid(string);
			}
		}
		throw new IllegalStateException("This is not friendly id");
	}

	private boolean looksLikeUuid(String value) {
		return value.contains("-");
	}
}
