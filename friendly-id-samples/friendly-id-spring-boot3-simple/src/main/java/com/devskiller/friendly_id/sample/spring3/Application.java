package com.devskiller.friendly_id.sample.spring3;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.friendly_id.type.FriendlyId;

@RestController
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/items/{id}")
	Item getItem(@PathVariable UUID id) {
		return new Item(id, id, id, FriendlyId.of(id));
	}

	@PostMapping("/items")
	Item createItem(@RequestBody Item item) {
		if (item.id() == null) {
			var uuid = UUID.randomUUID();
			return new Item(uuid, uuid, uuid, FriendlyId.of(uuid));
		}
		return item;
	}

}
