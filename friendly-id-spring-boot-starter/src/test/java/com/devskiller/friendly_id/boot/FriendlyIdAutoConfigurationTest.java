package com.devskiller.friendly_id.boot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import com.devskiller.friendly_id.FriendlyIdEncoding;
import com.devskiller.friendly_id.FriendlyIds;

import static org.assertj.core.api.Assertions.assertThat;

class FriendlyIdAutoConfigurationTest {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(FriendlyIdAutoConfiguration.class));

	@AfterEach
	void restoreDefaultEncoding() {
		FriendlyIds.setEncoding(FriendlyIdEncoding.STANDARD);
	}

	@Test
	void usesStandardEncodingByDefault() {
		contextRunner.run(context -> {
			assertThat(context).hasSingleBean(FriendlyIdAutoConfiguration.class);
			assertThat(FriendlyIds.getEncoding()).isEqualTo(FriendlyIdEncoding.STANDARD);
		});
	}

	@Test
	void encodingPropertySwitchesToLegacy() {
		contextRunner
				.withPropertyValues("com.devskiller.friendly-id.encoding=legacy")
				.run(context -> {
					assertThat(context).hasSingleBean(FriendlyIdAutoConfiguration.class);
					assertThat(FriendlyIds.getEncoding()).isEqualTo(FriendlyIdEncoding.LEGACY);
				});
	}

	@Test
	void canBeDisabled() {
		contextRunner
				.withPropertyValues("com.devskiller.friendly-id.enabled=false")
				.run(context -> assertThat(context).doesNotHaveBean(FriendlyIdAutoConfiguration.class));
	}

}
