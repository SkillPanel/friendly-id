package com.devskiller.friendly_id.jackson;

import java.util.UUID;

import tools.jackson.databind.module.SimpleModule;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.type.FriendlyId;

/**
 * Jackson 3 module for FriendlyId serialization/deserialization.
 * <p>
 * This module registers custom serializers and deserializers for UUID and FriendlyId types,
 * enabling automatic conversion between UUID values and their FriendlyId string representation.
 * </p>
 * <p>
 * By default, UUIDs are serialized as Base62 FriendlyIds. Use {@link FriendlyIdFormat#UUID}
 * to serialize UUIDs in standard format while still accepting both formats on deserialization.
 * Per-field format can always be overridden with {@link com.devskiller.friendly_id.IdFormat @IdFormat}.
 * </p>
 */
public class FriendlyIdModule extends SimpleModule {

	/**
	 * Creates a module with default FriendlyId (Base62) serialization format.
	 */
	public FriendlyIdModule() {
		this(FriendlyIdFormat.URL62);
	}

	/**
	 * Creates a module with the specified default serialization format.
	 * <p>
	 * When {@link FriendlyIdFormat#UUID} is used, UUIDs are serialized in standard format
	 * but deserialization still accepts both standard UUIDs and Base62 FriendlyIds.
	 * Per-field format can be overridden with {@link com.devskiller.friendly_id.IdFormat @IdFormat}.
	 *
	 * @param defaultFormat the default serialization format for UUID fields
	 */
	public FriendlyIdModule(FriendlyIdFormat defaultFormat) {
		super("FriendlyIdModule");
		boolean useFriendlyFormat = defaultFormat == FriendlyIdFormat.URL62;

		// UUID serializers/deserializers — deserializer always accepts both formats
		addSerializer(UUID.class, new FriendlyIdSerializer(useFriendlyFormat));
		addDeserializer(UUID.class, new FriendlyIdDeserializer());

		// FriendlyId value object serializers/deserializers
		addSerializer(FriendlyId.class, new FriendlyIdValueSerializer());
		addDeserializer(FriendlyId.class, new FriendlyIdValueDeserializer());
	}
}
