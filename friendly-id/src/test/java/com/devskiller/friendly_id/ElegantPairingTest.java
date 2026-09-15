package com.devskiller.friendly_id;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.IntStream;

import static com.devskiller.friendly_id.ElegantPairing.pair;
import static com.devskiller.friendly_id.ElegantPairing.sqrt;
import static com.devskiller.friendly_id.ElegantPairing.unpair;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class ElegantPairingTest {

	private static final long[] EXTREMES = {0, 1, -1, Long.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE - 1, Long.MIN_VALUE + 1};

	@Test
	void sqrtShouldMatchFloorSqrtForSmallValues() {
		for (long n = 0; n < 10_000; n++) {
			assertThat(sqrt(valueOf(n))).as("sqrt(%d)", n).isEqualTo(valueOf(n).sqrt());
		}
	}

	// pairing two longs yields values up to ~2^130, so roots span up to 66 bits
	@ParameterizedTest
	@MethodSource("rootBitLengths")
	void sqrtShouldMatchFloorSqrtAroundPerfectSquares(int rootBitLength) {
		Random random = new Random(rootBitLength);
		for (int i = 0; i < 1000; i++) {
			BigInteger root = new BigInteger(rootBitLength, random).setBit(rootBitLength - 1);
			BigInteger square = root.multiply(root);

			assertThat(sqrt(square.subtract(ONE))).isEqualTo(square.subtract(ONE).sqrt());
			assertThat(sqrt(square)).isEqualTo(root);
			assertThat(sqrt(square.add(ONE))).isEqualTo(root);
		}
	}

	@Test
	void pairingExtremeLongsShouldBeReversible() {
		for (long x : EXTREMES) {
			for (long y : EXTREMES) {
				BigInteger paired = pair(valueOf(x), valueOf(y));

				assertThat(sqrt(paired)).isEqualTo(paired.sqrt());
				assertThat(unpair(paired)).containsExactly(valueOf(x), valueOf(y));
			}
		}
	}

	static IntStream rootBitLengths() {
		return IntStream.rangeClosed(1, 66);
	}

}
