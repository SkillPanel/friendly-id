package com.devskiller.friendly_id.boot;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

import com.devskiller.friendly_id.spring.EnableFriendlyId;

/**
 * Auto-configuration for FriendlyId integration with Spring Boot.
 * <p>
 * Automatically enables FriendlyId converters and Jackson module when Spring Boot is detected.
 * Can be disabled by setting {@code com.devskiller.friendly-id.enabled=false} in application properties.
 */
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnProperty(
		prefix = "com.devskiller.friendly-id",
		name = "enabled",
		havingValue = "true",
		matchIfMissing = true
)
@EnableFriendlyId
public class FriendlyIdAutoConfiguration {

}
