package com.devskiller.friendly_id.openfeign;

import java.lang.reflect.Type;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

/**
 * Feign encoder that converts UUID and FriendlyId objects to FriendlyId strings in requests.
 * <p>
 * This encoder wraps the default encoder and intercepts UUID and FriendlyId parameters,
 * converting them to their FriendlyId string representation before sending the request.
 * </p>
 * <p>
 * Supported conversions:
 * </p>
 * <ul>
 *   <li>{@link UUID} → FriendlyId string</li>
 *   <li>{@link com.devskiller.friendly_id.type.FriendlyId} → FriendlyId string</li>
 * </ul>
 *
 * @see FriendlyIdDecoder
 * @since 1.1.1
 */
public class FriendlyIdEncoder implements Encoder {

	private final Encoder delegate;

	public FriendlyIdEncoder(Encoder delegate) {
		this.delegate = delegate;
	}

	@Override
	public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
		if (object instanceof UUID uuid) {
			delegate.encode(FriendlyId.toFriendlyId(uuid), bodyType, template);
		} else if (object instanceof com.devskiller.friendly_id.type.FriendlyId friendlyId) {
			delegate.encode(friendlyId.toString(), bodyType, template);
		} else {
			delegate.encode(object, bodyType, template);
		}
	}
}
