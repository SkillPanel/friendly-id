package com.devskiller.friendly_id.jackson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Declares that a field should be formatted as a friendly ID.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IdFormat {

	FriendlyIdFormat value() default FriendlyIdFormat.URL62;

}
