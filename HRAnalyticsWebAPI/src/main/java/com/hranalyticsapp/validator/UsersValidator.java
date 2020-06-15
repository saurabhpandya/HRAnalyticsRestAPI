package com.hranalyticsapp.validator;

import org.springframework.data.util.Pair;

public class UsersValidator extends Validator {

	private String emailRegEx = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private String passwordRegEx = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	private String msg_enter_valid_email = "Please enter valid email";
	private String msg_enter_valid_password = "Please enter valid password";
	private String msg_password_not_match = "Password does not match";

	public Pair<Boolean, String> isEmailValid(String email) {
		boolean isValid = false;
		String message = "";
		Pair<Boolean, String> emailNullOrEmptyPair = isNullOrEmpty(email);

		if (emailNullOrEmptyPair.getFirst()) {
			if (email.matches(emailRegEx)) {
				isValid = true;
			} else {
				isValid = false;
				message = msg_enter_valid_email;
			}
		} else {
			isValid = emailNullOrEmptyPair.getFirst();
			message = emailNullOrEmptyPair.getSecond();
		}
		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> isPasswordValid(String password) {
		boolean isValid = false;
		String message = "";
		Pair<Boolean, String> passwordNullOrEmptyPair = isNullOrEmpty(password);

		if (passwordNullOrEmptyPair.getFirst()) {
			if (password.matches(passwordRegEx)) {
				isValid = true;
			} else {
				isValid = false;
				message = msg_enter_valid_password;
			}
		} else {
			isValid = passwordNullOrEmptyPair.getFirst();
			message = passwordNullOrEmptyPair.getSecond();
		}
		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> validatePassword(String requestPassword, String dbPassword) {
		boolean isValid = false;
		String message = "";
		if (requestPassword.equals(dbPassword)) {
			isValid = true;
		} else {
			isValid = false;
			message = msg_password_not_match;
		}
		return Pair.of(isValid, message);
	}

}
