package com.devskiller.friendly_id.boot;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.devskiller.friendly_id.FriendlyIds;
import com.devskiller.friendly_id.spring.EnableFriendlyId;

/**
 * Auto-configuration for FriendlyId integration with Spring Boot.
 * <p>
 * Automatically enables FriendlyId converters and Jackson module when Spring Boot is detected.
 * Can be disabled by setting {@code com.devskiller.friendly-id.enabled=false} in application properties.
 * <p>
 * The encoding can be selected with {@code com.devskiller.friendly-id.encoding} — set it to
 * {@code legacy} to stay wire-compatible with identifiers issued by the friendly-id 1.0.x line.
 */
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnProperty(
		prefix = "com.devskiller.friendly-id",
		name = "enabled",
		havingValue = "true",
		matchIfMissing = true
)
@EnableConfigurationProperties(FriendlyIdProperties.class)
@EnableFriendlyId
public class FriendlyIdAutoConfiguration {

	FriendlyIdAutoConfiguration(FriendlyIdProperties properties) {
		FriendlyIds.setEncoding(properties.getEncoding());
	}

}
