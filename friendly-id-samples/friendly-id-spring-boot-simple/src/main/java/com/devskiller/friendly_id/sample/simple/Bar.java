package com.devskiller.friendly_id.sample.simple;

import java.util.UUID;

public class Bar {

	private UUID id;

	public Bar() {
	}

	public Bar(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
