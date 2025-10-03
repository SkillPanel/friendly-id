package com.devskiller.friendly_id;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Url62Test {

	@Test
	void shouldExplodeWhenContainsIllegalCharacters() {
		assertThatThrownBy(() -> Url62.decode("Foo Bar"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("contains illegal characters");
	}

	@Test
	void shouldFaildOnEmptyString() {
		assertThatThrownBy(() -> Url62.decode(""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("must not be empty");
	}

	@Test
	void shouldFailsOnNullString() {
		assertThatThrownBy(() -> Url62.decode(null))
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("must not be null");
	}

	@Test
	void shouldFailsWhenStringContainsMoreThan128bitInformation() {
		assertThatThrownBy(() -> Url62.decode("7NLCAyd6sKR7kDHxgAWFPas"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("contains more than 128bit information");
	}
}