package com.devskiller.friendly_id.sample.customized;

import java.util.UUID;

import com.devskiller.friendly_id.IdFormat;

import static com.devskiller.friendly_id.FriendlyIdFormat.RAW;

record Bar(UUID friendlyId, @IdFormat(RAW) UUID uuid) {
}
