package com.devskiller.friendly_id.jackson2;

import java.util.UUID;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import com.devskiller.friendly_id.IdFormat;

public class FriendlyIdAnnotationIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	@Override
	public Object findSerializer(Annotated annotatedMethod) {
		IdFormat annotation = _findAnnotation(annotatedMethod, IdFormat.class);
		if (annotatedMethod.getRawType() == UUID.class) {
			if (annotation != null) {
				return switch (annotation.value()) {
					case RAW -> UUIDSerializer.class;
					case URL62 -> FriendlyIdSerializer.class;
				};
			}
			return FriendlyIdSerializer.class;
		} else {
			return null;
		}
	}

	@Override
	public Object findDeserializer(Annotated annotatedMethod) {
		var annotation = _findAnnotation(annotatedMethod, IdFormat.class);
		if (rawDeserializationType(annotatedMethod) == UUID.class) {
			if (annotation != null) {
				return switch (annotation.value()) {
					case RAW -> UUIDDeserializer.class;
					case URL62 -> FriendlyIdDeserializer.class;
				};
			}
			return FriendlyIdDeserializer.class;
		}
		return null;
	}

	private Class<?> rawDeserializationType(Annotated a) {
		if (a instanceof AnnotatedMethod am && am.getParameterCount() == 1) {
			return am.getRawParameterType(0);
		}
		return a.getRawType();
	}
}
