package com.devskiller.friendly_id.spring;

import java.util.UUID;

import com.devskiller.friendly_id.FriendlyIdFormat;
import com.devskiller.friendly_id.IdFormat;

public record Foo(
		@IdFormat(FriendlyIdFormat.UUID) UUID rawUuid,
		UUID friendlyId
) {}
