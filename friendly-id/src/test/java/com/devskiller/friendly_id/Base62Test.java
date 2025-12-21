package com.devskiller.friendly_id;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static com.devskiller.friendly_id.IdUtil.areEqualIgnoringLeadingZeros;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class Base62Test {

	@Test
	void decodingValuePrefixedWithZeros() {
		assertThat(Base62.encode(Base62.decode("00001"))).isEqualTo("1");
		assertThat(Base62.encode(Base62.decode("01001"))).isEqualTo("1001");
		assertThat(Base62.encode(Base62.decode("00abcd"))).isEqualTo("abcd");
	}

	@Test
	void shouldCheck128BitLimits() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
				Base62.decode("1Vkp6axDWu5pI3q1xQO3oO0"));
	}

	@RepeatedTest(1000)
	void decodingIdShouldBeReversible() {
		String id = generateRandomFriendlyId();
		String result = Base62.encode(Base62.decode(id));
		assertThat(areEqualIgnoringLeadingZeros(result, id)).isTrue();
	}

	@RepeatedTest(1000)
	void encodingNumberShouldBeReversible() {
		BigInteger bigInteger = new BigInteger(128, new Random());
		BigInteger result = Base62.decode(Base62.encode(bigInteger));
		assertThat(result).isEqualTo(bigInteger);
	}

	private String generateRandomFriendlyId() {
		Random random = new Random();
		BigInteger bigInt = new BigInteger(128, random);
		return Base62.encode(bigInt);
	}

}
