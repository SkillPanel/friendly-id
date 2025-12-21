package com.devskiller.friendly_id.sample.spring3;

import java.util.UUID;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.IdFormat;
import com.devskiller.friendly_id.type.FriendlyId;

/**
 * Example record demonstrating different UUID serialization formats.
 *
 * @param id           UUID serialized as FriendlyId string (default behavior)
 * @param rawId        UUID serialized as raw UUID string
 * @param friendlyUuid UUID explicitly serialized as FriendlyId string
 * @param friendlyId   FriendlyId value object type
 */
public record Item(
		UUID id,
		@IdFormat(FriendlyIdFormat.RAW) UUID rawId,
		@IdFormat(FriendlyIdFormat.URL62) UUID friendlyUuid,
		FriendlyId friendlyId
) {
}
