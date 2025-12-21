package com.devskiller.friendly_id;

import java.util.UUID;

/**
 * Class to convert UUID to url Friendly IDs basing on Url62.
 *
 * @deprecated Use {@link FriendlyIds} instead. This class will be removed in a future version.
 */
@Deprecated(since = "2.0", forRemoval = true)
public class FriendlyId {

	/**
	 * Create FriendlyId id
	 *
	 * @return Friendly Id encoded UUID
	 * @deprecated Use {@link FriendlyIds#createFriendlyId()} instead.
	 */
	@Deprecated(since = "2.0", forRemoval = true)
	public static String createFriendlyId() {
		return FriendlyIds.createFriendlyId();
	}

	/**
	 * Encode UUID to FriendlyId id
	 *
	 * @param uuid UUID to be encoded
	 * @return Friendly Id encoded UUID
	 * @deprecated Use {@link FriendlyIds#toFriendlyId(UUID)} instead.
	 */
	@Deprecated(since = "2.0", forRemoval = true)
	public static String toFriendlyId(UUID uuid) {
		return FriendlyIds.toFriendlyId(uuid);
	}

	/**
	 * Decode Friendly Id to UUID
	 *
	 * @param friendlyId encoded UUID
	 * @return decoded UUID
	 * @deprecated Use {@link FriendlyIds#toUuid(String)} instead.
	 */
	@Deprecated(since = "2.0", forRemoval = true)
	public static UUID toUuid(String friendlyId) {
		return FriendlyIds.toUuid(friendlyId);
	}

}
