package com.devskiller.friendly_id.spring;

import java.util.UUID;

import com.devskiller.friendly_id.jackson.FriendlyIdFormat;
import com.devskiller.friendly_id.jackson.IdFormat;

public class Bar {

	@IdFormat(FriendlyIdFormat.RAW)
	private final UUID rawUuid;

	private final UUID friendlyId;

	public Bar(UUID rawUuid, UUID friendlyId) {
		this.rawUuid = rawUuid;
		this.friendlyId = friendlyId;
	}

	public UUID getRawUuid() {
		return rawUuid;
	}

	public UUID getFriendlyId() {
		return friendlyId;
	}


}
