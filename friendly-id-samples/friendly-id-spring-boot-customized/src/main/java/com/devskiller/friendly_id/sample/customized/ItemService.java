package com.devskiller.friendly_id.sample.customized;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.type.FriendlyId;

@Slf4j
@Service
public class ItemService {

	public Item find(UUID uuid) {
		log.info("find: {}", uuid);
		return new Item(uuid, uuid, uuid, FriendlyId.of(uuid));
	}

	public Item create(Item item) {
		if (item.id() == null) {
			var uuid = UUID.randomUUID();
			return new Item(uuid, uuid, uuid, FriendlyId.of(uuid));
		}
		return item;
	}

	public void update(UUID id, Item item) {
		log.info("update: {}:{}", id, item);
	}
}
