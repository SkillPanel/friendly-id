package com.devskiller.friendly_id.sample.customized;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FooService {

	public Bar find(UUID uuid) {
		log.info("find: {}",uuid);
		return new Bar(uuid, uuid);
	}

	public void update(UUID id, Bar bar) {
		log.info("update: {}:{}", id, bar);
	}
}
