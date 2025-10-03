package com.devskiller.friendly_id.sample.contracts.domain;

import java.util.UUID;

public record Bar(UUID id, String name, Foo foo) {
}
