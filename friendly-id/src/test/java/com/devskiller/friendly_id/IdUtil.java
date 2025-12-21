package com.devskiller.friendly_id;

import java.util.Objects;

class IdUtil {

	static boolean areEqualIgnoringLeadingZeros(String code1, String code2) {
		return Objects.equals(removeLeadingZeros(code1), removeLeadingZeros(code2));
	}

	private static String removeLeadingZeros(String string) {
		return string.replaceFirst("^0+(?!$)", "");
	}

}
