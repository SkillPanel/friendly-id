package com.devskiller.friendly_id;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Class to convert UUID to Url62 IDs
 */
class Url62 {

	/**
	 * Encode UUID to Url62 id
	 *
	 * @param uuid UUID to be encoded
	 * @return url62 encoded UUID
	 */
	static String encode(UUID uuid) {
		BigInteger pair = UuidConverter.toBigInteger(uuid, FriendlyIds.getEncoding());
		return Base62.encode(pair);
	}

	/**
	 * Decode url62 id to UUID
	 *
	 * @param id url62 encoded id
	 * @return decoded UUID
	 */
	static UUID decode(String id) {
		BigInteger decoded = Base62.decode(id);
		return UuidConverter.toUuid(decoded, FriendlyIds.getEncoding());
	}

}
