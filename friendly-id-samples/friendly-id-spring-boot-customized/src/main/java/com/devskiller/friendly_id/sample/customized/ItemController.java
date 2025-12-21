package com.devskiller.friendly_id.sample.customized;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/{id}")
	public Item get(@PathVariable UUID id) {
		log.info("get {}", id);
		return itemService.find(id);
	}

	@PostMapping
	public Item create(@RequestBody Item item) {
		log.info("create {}", item);
		return itemService.create(item);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable UUID id, @RequestBody Item body) {
		itemService.update(id, body);
	}
}
