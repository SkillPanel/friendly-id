package com.devskiller.friendly_id.openfeign;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

/**
 * Feign decoder that converts FriendlyId strings to UUID and FriendlyId objects in responses.
 * <p>
 * This decoder wraps the default decoder and intercepts String responses that should be
 * converted to UUID or FriendlyId value objects.
 * </p>
 * <p>
 * Supported conversions:
 * </p>
 * <ul>
 *   <li>FriendlyId string → {@link UUID}</li>
 *   <li>FriendlyId string → {@link com.devskiller.friendly_id.type.FriendlyId}</li>
 * </ul>
 *
 * @see FriendlyIdEncoder
 * @since 1.1.1
 */
public class FriendlyIdDecoder implements Decoder {

	private final Decoder delegate;

	public FriendlyIdDecoder(Decoder delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
		Object decoded = delegate.decode(response, type);

		if (type == UUID.class && decoded instanceof String stringValue) {
			return FriendlyId.toUuid(stringValue);
		}

		if (type == com.devskiller.friendly_id.type.FriendlyId.class && decoded instanceof String stringValue) {
			return com.devskiller.friendly_id.type.FriendlyId.fromString(stringValue);
		}

		return decoded;
	}
}
