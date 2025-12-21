package com.devskiller.friendly_id.spring;

import java.util.UUID;

import com.devskiller.friendly_id.jackson.FriendlyIdFormat;
import com.devskiller.friendly_id.jackson.IdFormat;

public class Foo {

	@IdFormat(FriendlyIdFormat.RAW)
	private UUID rawUuid;

	private UUID friendlyId;

	public UUID getRawUuid() {
		return rawUuid;
	}

	public void setRawUuid(UUID rawUuid) {
		this.rawUuid = rawUuid;
	}

	public UUID getFriendlyId() {
		return friendlyId;
	}

	public void setFriendlyId(UUID friendlyId) {
		this.friendlyId = friendlyId;
	}
}
