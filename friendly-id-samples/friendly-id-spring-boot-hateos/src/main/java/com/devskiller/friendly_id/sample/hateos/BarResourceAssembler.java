package com.devskiller.friendly_id.sample.hateos;

import com.devskiller.friendly_id.sample.hateos.domain.Bar;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BarResourceAssembler extends RepresentationModelAssemblerSupport<Bar, BarResource> {

	public BarResourceAssembler() {
		super(BarController.class, BarResource.class);
	}

	@Override
	public BarResource toModel(Bar entity) {
		BarResource resource = new BarResource(entity.name());

		// Modern Spring HATEOAS 2.x - methodOn() triggers automatic FriendlyId conversion
		resource.add(linkTo(FooController.class).withRel("foos"));
		resource.add(linkTo(methodOn(BarController.class)
				.getBar(entity.foo().id(), entity.id()))
				.withSelfRel());

		return resource;
	}

}
