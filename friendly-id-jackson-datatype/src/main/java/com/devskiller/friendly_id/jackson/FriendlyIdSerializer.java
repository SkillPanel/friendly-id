package com.devskiller.friendly_id.jackson;

import java.util.UUID;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.ser.std.StdSerializer;

import com.devskiller.friendly_id.FriendlyIds;
import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.IdFormat;

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
	public void serialize(UUID uuid, JsonGenerator jsonGenerator, SerializationContext ctxt) {
		if (useFriendlyFormat) {
			jsonGenerator.writeString(FriendlyIds.toFriendlyId(uuid));
		} else {
			jsonGenerator.writeString(uuid.toString());
		}
	}

	@Override
	public ValueSerializer<?> createContextual(SerializationContext ctxt, BeanProperty property) {
		if (property != null) {
			IdFormat annotation = property.getAnnotation(IdFormat.class);
			if (annotation != null && annotation.value() == FriendlyIdFormat.UUID) {
				return new FriendlyIdSerializer(false);
			}
		}
		return this;
	}
}
