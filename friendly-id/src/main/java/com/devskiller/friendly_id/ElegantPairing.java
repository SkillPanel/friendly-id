package com.devskiller.friendly_id;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

/**
 * https://stackoverflow.com/questions/919612/mapping-two-integers-to-one-in-a-unique-and-deterministic-way/13871379#13871379
 */
class ElegantPairing {

	private ElegantPairing() {
	}

	static BigInteger pair(BigInteger first, BigInteger second) {
		BigInteger a = first.signum() >= 0 ? TWO.multiply(first) : TWO.negate().multiply(first).subtract(ONE);
		BigInteger b = second.signum() >= 0 ? TWO.multiply(second) : TWO.negate().multiply(second).subtract(ONE);
		if (a.compareTo(b) >= 0) {
			return a.multiply(a).add(a).add(b);
		} else {
			return b.multiply(b).add(a);
		}
	}

	static BigInteger[] unpair(BigInteger value) {
		BigInteger a = sqrt(value);
		BigInteger b = value.subtract(a.multiply(a));
		return a.compareTo(b) > 0 ?
				new BigInteger[]{recoverSignedValue(b), recoverSignedValue(a)} :
				new BigInteger[]{recoverSignedValue(a), recoverSignedValue(b.subtract(a))};
	}

	private static BigInteger recoverSignedValue(BigInteger value) {
		return value.testBit(0) ? value.divide(TWO).negate().subtract(ONE) : value.divide(TWO);
	}

	/**
	 * Returns floor(sqrt(n)) for a non-negative {@code n}, the same result as the binary search used by 1.0.x.
	 * <p>
	 * A double estimate is accurate to ~53 bits, one Newton step fixes the rest of a root of up to 64 bits
	 * (paired UUIDs are below 2^128), and the loops correct the final off-by-one.
	 * <p>
	 * TODO: replace with {@link BigInteger#sqrt()} after moving to JDK 25 — it is ~9x slower than this on
	 * JDK 21 but faster on JDK 25.
	 */
	static BigInteger sqrt(BigInteger n) {
		if (n.signum() == 0) {
			return n;
		}
		double root = Math.sqrt(n.doubleValue());
		// bits below the 53 significant ones are zero, so scaling them off keeps the conversion exact
		int shift = Math.max(0, Math.getExponent(root) - 52);
		BigInteger a = BigInteger.valueOf((long) Math.scalb(root, -shift)).shiftLeft(shift);
		a = a.add(n.divide(a)).shiftRight(1);
		while (a.multiply(a).compareTo(n) > 0) {
			a = a.subtract(ONE);
		}
		for (BigInteger next = a.add(ONE); next.multiply(next).compareTo(n) <= 0; next = a.add(ONE)) {
			a = next;
		}
		return a;
	}

}
