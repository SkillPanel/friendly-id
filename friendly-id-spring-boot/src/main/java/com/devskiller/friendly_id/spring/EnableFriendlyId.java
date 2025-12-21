package com.devskiller.friendly_id.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Enable FriendlyId support in Spring MVC applications.
 * <p>
 * Add this annotation to a {@code @Configuration} class to enable automatic conversion
 * between FriendlyId strings and UUIDs in:
 * <ul>
 *   <li>Path variables ({@code @PathVariable UUID id})</li>
 *   <li>Request parameters ({@code @RequestParam UUID id})</li>
 *   <li>JSON request/response bodies (via Jackson)</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>
 * &#64;Configuration
 * &#64;EnableFriendlyId
 * public class WebConfig {
 * }
 * </pre>
 * <p>
 * <strong>Note:</strong> When using {@code spring-boot-starter-friendly-id}, this configuration
 * is applied automatically and this annotation is not required.
 *
 * @see FriendlyIdConfiguration
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FriendlyIdConfiguration.class)
public @interface EnableFriendlyId {

}
