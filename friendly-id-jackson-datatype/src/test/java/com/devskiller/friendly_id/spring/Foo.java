package com.devskiller.friendly_id.spring;

import java.util.UUID;

import com.devskiller.friendly_id.jackson.FriendlyIdFormat;
import com.devskiller.friendly_id.jackson.IdFormat;

public record Foo(
		@IdFormat(FriendlyIdFormat.RAW) UUID rawUuid,
		UUID friendlyId
) {}
