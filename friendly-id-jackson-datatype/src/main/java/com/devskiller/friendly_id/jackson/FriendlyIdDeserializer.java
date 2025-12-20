package com.devskiller.friendly_id.jackson;

import java.util.UUID;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;

import com.devskiller.friendly_id.FriendlyId;

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
	public UUID deserialize(JsonParser parser, DeserializationContext deserializationContext) {
		JsonToken token = parser.currentToken();
		if (token == JsonToken.VALUE_STRING) {
			String string = parser.getString().trim();
			if (useFriendlyFormat) {
				if (looksLikeUuid(string)) {
					return UUID.fromString(string);
				} else {
					return FriendlyId.toUuid(string);
				}
			} else {
				return UUID.fromString(string);
			}
		}
		throw new IllegalStateException("This is not friendly id");
	}

	private boolean looksLikeUuid(String value) {
		return value.contains("-");
	}

	@Override
	public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
		if (property != null) {
			IdFormat annotation = property.getAnnotation(IdFormat.class);
			if (annotation != null && annotation.value() == FriendlyIdFormat.RAW) {
				return new FriendlyIdDeserializer(false);
			}
		}
		return this;
	}
}
