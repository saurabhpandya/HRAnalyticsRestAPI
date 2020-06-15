package com.hranalyticsapp.validator;

import org.springframework.data.util.Pair;

public class Validator {

	private String nullOrEmpty = "Please enter some data";

	public Pair<Boolean, String> isNullOrEmpty(String str) {

		boolean isValid = false;
		String message = "";

		if (str != null && !str.isEmpty()) {
			isValid = true;
		} else {
			isValid = false;
			message = nullOrEmpty;
		}

		return Pair.of(isValid, message);
	}

}
