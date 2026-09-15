package com.devskiller.friendly_id;

import java.math.BigInteger;
import java.util.UUID;

class UuidConverter {

	static BigInteger toBigInteger(UUID uuid, FriendlyIdEncoding encoding) {
		return encoding.pair(
				BigInteger.valueOf(uuid.getMostSignificantBits()),
				BigInteger.valueOf(uuid.getLeastSignificantBits())
		);
	}

	static UUID toUuid(BigInteger value, FriendlyIdEncoding encoding) {
		BigInteger[] unpaired = encoding.unpair(value);
		return new UUID(unpaired[0].longValueExact(), unpaired[1].longValueExact());
	}

}
