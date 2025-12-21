package com.devskiller.friendly_id.sample.contracts;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.friendly_id.type.FriendlyId;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

	@GetMapping("/{id}")
	public Item get(@PathVariable UUID id) {
		log.info("Get {}", id);
		return new Item(id, id, id, FriendlyId.of(id));
	}

	@PostMapping
	public Item create(@RequestBody Item item) {
		log.info("Create {}", item);
		var uuid = item.id() != null ? item.id() : UUID.randomUUID();
		return new Item(uuid, uuid, uuid, FriendlyId.of(uuid));
	}
}
