package com.devskiller.friendly_id.jackson;

import java.util.UUID;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;

import com.devskiller.friendly_id.FriendlyId;
import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.IdFormat;

public class FriendlyIdDeserializer extends StdDeserializer<UUID> {

	private final boolean useFriendlyFormat;

	public FriendlyIdDeserializer() {
		this(true);
	}

	private FriendlyIdDeserializer(boolean useFriendlyFormat) {
		super(UUID.class);
		this.useFriendlyFormat = useFriendlyFormat;
	}

	@Override
	public UUID deserialize(JsonParser parser, DeserializationContext ctxt) {
		var token = parser.currentToken();
		if (token == JsonToken.VALUE_STRING) {
			var value = parser.getString().trim();
			if (useFriendlyFormat) {
				return parseAsUuidOrFriendlyId(value);
			} else {
				return UUID.fromString(value);
			}
		}
		throw ctxt.weirdStringException(parser.getString(), UUID.class, "Expected UUID string value");
	}

	/**
	 * Attempts to parse the value as a standard UUID first, then falls back to FriendlyId format.
	 * This approach is more robust than heuristic-based detection.
	 */
	private UUID parseAsUuidOrFriendlyId(String value) {
		if (isStandardUuidFormat(value)) {
			return UUID.fromString(value);
		}
		return FriendlyId.toUuid(value);
	}

	/**
	 * Checks if the string matches standard UUID format (36 chars with hyphens at positions 8, 13, 18, 23).
	 */
	private boolean isStandardUuidFormat(String value) {
		return value.length() == 36
				&& value.charAt(8) == '-'
				&& value.charAt(13) == '-'
				&& value.charAt(18) == '-'
				&& value.charAt(23) == '-';
	}

	@Override
	public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
		if (property != null) {
			var annotation = property.getAnnotation(IdFormat.class);
			if (annotation != null && annotation.value() == FriendlyIdFormat.RAW) {
				return new FriendlyIdDeserializer(false);
			}
		}
		return this;
	}
}
