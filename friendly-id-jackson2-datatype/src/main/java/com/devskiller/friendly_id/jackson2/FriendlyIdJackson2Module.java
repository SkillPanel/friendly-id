package com.devskiller.friendly_id.jackson2;

import java.util.UUID;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.type.FriendlyId;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class FriendlyIdJackson2Module extends SimpleModule {

	private final FriendlyIdAnnotationIntrospector introspector;

	/**
	 * Creates a module with default FriendlyId (Base62) serialization format.
	 */
	public FriendlyIdJackson2Module() {
		this(FriendlyIdFormat.URL62);
	}

	/**
	 * Creates a module with the specified default serialization format.
	 *
	 * @param defaultFormat the default serialization format for UUID fields
	 */
	public FriendlyIdJackson2Module(FriendlyIdFormat defaultFormat) {
		boolean useFriendlyFormat = defaultFormat == FriendlyIdFormat.URL62;
		introspector = new FriendlyIdAnnotationIntrospector();
		addDeserializer(UUID.class, new FriendlyIdDeserializer());
		addSerializer(UUID.class, new FriendlyIdSerializer(useFriendlyFormat));

		// Add serializer/deserializer for FriendlyId value object
		addDeserializer(FriendlyId.class, new FriendlyIdValueDeserializer());
		addSerializer(FriendlyId.class, new FriendlyIdValueSerializer());
	}

	@Override
	public void setupModule(SetupContext context) {
		super.setupModule(context);
		context.insertAnnotationIntrospector(introspector);
	}
}
