package com.devskiller.friendly_id.sample.hateos.domain;

import java.util.UUID;

public record Bar(UUID id, String name, Foo foo) {
}
