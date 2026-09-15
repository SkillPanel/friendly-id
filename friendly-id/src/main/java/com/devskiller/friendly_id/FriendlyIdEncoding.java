package com.devskiller.friendly_id;

import java.math.BigInteger;

/**
 * Strategy used to map the two 64-bit halves of a UUID onto the single
 * {@link BigInteger} that is Base62-encoded into a FriendlyId string.
 * <p>
 * The two strategies produce <strong>incompatible</strong> FriendlyId strings for
 * the same UUID. Decoding a FriendlyId with the wrong strategy does not fail —
 * it silently yields a different UUID — so a service must keep using the strategy
 * its identifiers were originally issued with.
 *
 * <ul>
 *   <li>{@link #STANDARD} — bit-shifting pairing, the default since 1.1.0</li>
 *   <li>{@link #LEGACY} — Szudzik's elegant pairing, used by the 1.0.x line</li>
 * </ul>
 *
 * @since 2.0.0-beta6
 * @see FriendlyIds#setEncoding(FriendlyIdEncoding)
 */
public enum FriendlyIdEncoding {

	/**
	 * Bit-shifting pairing ({@code hi * 2^64 + unsigned(lo)}), the default encoding
	 * since friendly-id 1.1.0.
	 */
	STANDARD {
		@Override
		BigInteger pair(BigInteger hi, BigInteger lo) {
			return BigIntegerPairing.pair(hi, lo);
		}

		@Override
		BigInteger[] unpair(BigInteger value) {
			return BigIntegerPairing.unpair(value);
		}
	},

	/**
	 * Szudzik's elegant pairing, the encoding used by the friendly-id 1.0.x line.
	 * Use this to stay wire-compatible with identifiers issued by 1.0.x.
	 */
	LEGACY {
		@Override
		BigInteger pair(BigInteger hi, BigInteger lo) {
			return ElegantPairing.pair(hi, lo);
		}

		@Override
		BigInteger[] unpair(BigInteger value) {
			return ElegantPairing.unpair(value);
		}
	};

	abstract BigInteger pair(BigInteger hi, BigInteger lo);

	abstract BigInteger[] unpair(BigInteger value);

}
