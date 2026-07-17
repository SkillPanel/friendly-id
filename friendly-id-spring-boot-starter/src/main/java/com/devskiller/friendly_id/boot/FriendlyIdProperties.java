package com.devskiller.friendly_id.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.devskiller.friendly_id.FriendlyIdEncoding;

/**
 * Configuration properties for the FriendlyId Spring Boot integration.
 */
@ConfigurationProperties(prefix = "com.devskiller.friendly-id")
public class FriendlyIdProperties {

	/**
	 * Whether to enable the FriendlyId auto-configuration.
	 */
	private boolean enabled = true;

	/**
	 * Encoding used for UUID to FriendlyId conversion. Use LEGACY to stay
	 * wire-compatible with identifiers issued by the friendly-id 1.0.x line.
	 */
	private FriendlyIdEncoding encoding = FriendlyIdEncoding.STANDARD;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public FriendlyIdEncoding getEncoding() {
		return encoding;
	}

	public void setEncoding(FriendlyIdEncoding encoding) {
		this.encoding = encoding;
	}

}
