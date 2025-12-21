package com.devskiller.friendly_id.sample.hateos;

import com.devskiller.friendly_id.sample.hateos.domain.Bar;
import com.devskiller.friendly_id.sample.hateos.domain.Foo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FooResourceAssembler extends RepresentationModelAssemblerSupport<Foo, FooResource> {

	private final BarResourceAssembler barResourceAssembler;

	public FooResourceAssembler(BarResourceAssembler barResourceAssembler) {
		super(FooController.class, FooResource.class);
		this.barResourceAssembler = barResourceAssembler;
	}

	@Override
	public FooResource toModel(Foo entity) {
		List<Bar> bars = Arrays.asList(
				new Bar(UUID.randomUUID(), "bar one", entity),
				new Bar(UUID.randomUUID(), "bar two", entity)
		);
		CollectionModel<BarResource> barResources = barResourceAssembler.toCollectionModel(bars);
		FooResource resource = new FooResource(entity.id(), entity.name(), barResources);

		// Modern Spring HATEOAS 2.x - methodOn() triggers automatic FriendlyId conversion
		resource.add(linkTo(methodOn(FooController.class)
				.get(entity.id()))
				.withSelfRel());

		return resource;
	}
}
