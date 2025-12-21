package com.devskiller.friendly_id.jackson;

import java.util.UUID;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

public class FriendlyIdAnnotationIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	@Override
	public Object findSerializer(Annotated annotatedMethod) {
		IdFormat annotation = _findAnnotation(annotatedMethod, IdFormat.class);
		if (annotatedMethod.getRawType() == UUID.class) {
			if (annotation != null) {
				switch (annotation.value()) {
					case RAW:
						return UUIDSerializer.class;
					case URL62:
						return FriendlyIdSerializer.class;
				}
			}
			return FriendlyIdSerializer.class;
		} else {
			return null;
		}
	}

	@Override
	public Object findDeserializer(Annotated annotatedMethod) {
		IdFormat annotation = _findAnnotation(annotatedMethod, IdFormat.class);
		if (rawDeserializationType(annotatedMethod) == UUID.class) {
			if (annotation != null) {
				switch (annotation.value()) {
					case RAW:
						return UUIDDeserializer.class;
					case URL62:
						return FriendlyIdDeserializer.class;
				}
			}
			return FriendlyIdDeserializer.class;
		} else {
			return null;
		}
	}

	private Class<?> rawDeserializationType(Annotated a) {
		if (a instanceof AnnotatedMethod) {
			AnnotatedMethod am = (AnnotatedMethod) a;
			if (am.getParameterCount() == 1) {
				return am.getRawParameterType(0);
			}
		}
		return a.getRawType();
	}
}
