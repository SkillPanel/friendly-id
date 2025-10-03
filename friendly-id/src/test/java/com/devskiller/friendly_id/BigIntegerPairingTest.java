package com.devskiller.friendly_id;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static com.devskiller.friendly_id.BigIntegerPairing.pair;
import static com.devskiller.friendly_id.BigIntegerPairing.unpair;
import static java.math.BigInteger.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class BigIntegerPairingTest {

	@Test
	void shouldPairTwoLongs() {
		long x = 1;
		long y = 2;

		BigInteger z = pair(valueOf(1), valueOf(2));

		assertThat(unpair(z)).contains(valueOf(x), valueOf(y));
	}

	@RepeatedTest(1000)
	void resultOfPairingShouldBePositive() {
		Random random = new Random();
		long x = random.nextLong();
		long y = random.nextLong();

		BigInteger paired = pair(valueOf(x), valueOf(y));

		assertThat(paired.signum()).isGreaterThan(0);
	}

	@RepeatedTest(1000)
	void pairingLongsShouldBeReversible() {
		Random random = new Random();
		long x = random.nextLong();
		long y = random.nextLong();

		BigInteger paired = pair(valueOf(x), valueOf(y));
		BigInteger[] unpaired = unpair(paired);

		assertThat(unpaired).containsExactly(valueOf(x), valueOf(y));
	}

}