package com.devskiller.friendly_id.sample.contracts;

import com.devskiller.friendly_id.sample.contracts.domain.Foo;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FooResourceAssembler extends RepresentationModelAssemblerSupport<Foo, FooResource> {

	public FooResourceAssembler() {
		super(FooController.class, FooResource.class);
	}

	@Override
	public FooResource toModel(Foo entity) {
		FooResource resource = new FooResource(entity.id(), entity.name());

		// Modern Spring HATEOAS 2.x - methodOn() triggers automatic FriendlyId conversion
		resource.add(linkTo(methodOn(FooController.class)
				.get(entity.id()))
				.withSelfRel());

		return resource;
	}
}
