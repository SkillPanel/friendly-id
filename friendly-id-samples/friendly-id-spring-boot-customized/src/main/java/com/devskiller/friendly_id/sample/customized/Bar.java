package com.devskiller.friendly_id.sample.customized;

import java.util.UUID;

import com.devskiller.friendly_id.jackson.IdFormat;

import static com.devskiller.friendly_id.jackson.FriendlyIdFormat.RAW;

record Bar(UUID friendlyId, @IdFormat(RAW) UUID uuid) {
}
