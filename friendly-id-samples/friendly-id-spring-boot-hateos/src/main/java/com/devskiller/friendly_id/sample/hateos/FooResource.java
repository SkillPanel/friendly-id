package com.devskiller.friendly_id.sample.hateos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;
import java.util.UUID;

@Relation(value = "foos")
public class FooResource extends RepresentationModel<FooResource> {

	private final UUID uuid;
	private final String name;
	@JsonUnwrapped
	private final CollectionModel<BarResource> embeddeds;

	// Full constructor
	public FooResource(UUID uuid, String name, CollectionModel<BarResource> embeddeds) {
		this.uuid = uuid;
		this.name = name;
		this.embeddeds = embeddeds;
	}

	// Constructor for creating resources without embedded collections (for deserialization)
	@JsonCreator
	public FooResource(@JsonProperty("uuid") UUID uuid, @JsonProperty("name") String name) {
		this(uuid, name, CollectionModel.of(List.of()));
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public CollectionModel<BarResource> getEmbeddeds() {
		return embeddeds;
	}

}
