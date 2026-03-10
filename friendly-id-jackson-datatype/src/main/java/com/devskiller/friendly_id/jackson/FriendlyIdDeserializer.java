package com.devskiller.friendly_id.jackson;

import java.util.UUID;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.IdFormat;

import static com.devskiller.friendly_id.type.FriendlyId.parse;

public class FriendlyIdDeserializer extends StdDeserializer<UUID> {

	private final boolean useFriendlyFormat;

	public FriendlyIdDeserializer() {
		this(true);
	}

	FriendlyIdDeserializer(boolean useFriendlyFormat) {
		super(UUID.class);
		this.useFriendlyFormat = useFriendlyFormat;
	}

	@Override
	public UUID deserialize(JsonParser parser, DeserializationContext ctxt) {
		var token = parser.currentToken();
		if (token == JsonToken.VALUE_STRING) {
			var value = parser.getString().trim();
			if (useFriendlyFormat) {
				return parse(value).toUuid();
			} else {
				return UUID.fromString(value);
			}
		}
		throw ctxt.weirdStringException(parser.getString(), UUID.class, "Expected UUID string value");
	}

	@Override
	public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
		if (property != null) {
			var annotation = property.getAnnotation(IdFormat.class);
			if (annotation != null && annotation.value() == FriendlyIdFormat.UUID) {
				return new FriendlyIdDeserializer(false);
			}
		}
		return this;
	}
}
