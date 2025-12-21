package com.devskiller.friendly_id.spring;

import java.util.UUID;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.IdFormat;

public record Bar(
		@IdFormat(FriendlyIdFormat.RAW) UUID rawUuid,
		UUID friendlyId
) {}
