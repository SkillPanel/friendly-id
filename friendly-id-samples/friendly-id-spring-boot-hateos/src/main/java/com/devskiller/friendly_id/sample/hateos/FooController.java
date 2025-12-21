package com.devskiller.friendly_id.sample.hateos;

import com.devskiller.friendly_id.sample.hateos.domain.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@ExposesResourceFor(FooResource.class)
@RequestMapping("/foos")
public class FooController {

	private final FooResourceAssembler assembler;

	public FooController(FooResourceAssembler assembler) {
		this.assembler = assembler;
	}

	@GetMapping("/{id}")
	public HttpEntity<FooResource> get(@PathVariable UUID id) {
		log.info("Get {}", id);
		Foo foo = new Foo(id, "Foo");

		FooResource fooResource = assembler.toModel(foo);
		return ResponseEntity.ok(fooResource);
	}

	@PutMapping("/{id}")
	public HttpEntity<FooResource> update(@PathVariable UUID id, @RequestBody FooResource fooResource) {
		log.info("Update {} : {}", id, fooResource);
		Foo entity = new Foo(fooResource.getUuid(), fooResource.getName());
		return ResponseEntity.ok(assembler.toModel(entity));
	}

	@PostMapping
	public HttpEntity<FooResource> create(@RequestBody FooResource fooResource) {
		log.info("Create {}", fooResource.getUuid());

		// Modern Spring HATEOAS 2.x - methodOn() triggers Spring's FriendlyId conversion
		URI location = linkTo(methodOn(FooController.class)
				.get(fooResource.getUuid()))
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
