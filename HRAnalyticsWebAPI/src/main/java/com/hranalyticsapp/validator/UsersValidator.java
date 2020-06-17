package com.hranalyticsapp.validator;

import org.springframework.data.util.Pair;

public class UsersValidator extends Validator {

	private String emailRegEx = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private String passwordRegEx = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	private String mobileRegEx = "^[0][1-9]\\d{9}$|^[6-9]\\d{9}$";

	private String msg_enter_valid_prefix = "Please enter valid prefix";
	private String msg_enter_valid_user_type = "Please enter valid user type";
	private String msg_enter_valid_firstname = "Please enter valid first name";
	private String msg_enter_valid_middlename = "Please enter valid middle name";
	private String msg_enter_valid_lastname = "Please enter valid last name";
	private String msg_enter_valid_marital_status = "Please enter valid marital status";
	private String msg_enter_valid_gender = "Please enter valid gender";
	private String msg_enter_valid_dob = "Please enter valid date of birth";
	private String msg_enter_valid_email = "Please enter valid email";
	private String msg_enter_valid_mobile = "Please enter valid mobile number";
	private String msg_enter_valid_password = "Please enter valid password";

	private String msg_password_not_match = "Password does not match";

	public Pair<Boolean, String> isPrefixValid(int prefix) {
		boolean isValid = false;
		String message = "";

		if (prefix > 0 && prefix <= 5) {
			isValid = true;
		} else {
			isValid = false;
			message = msg_enter_valid_prefix;
		}

		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> isUserTypeValid(int userType) {
		boolean isValid = false;
		String message = "";

		if (userType > 0 && userType <= 4) {
			isValid = true;
		} else {
			isValid = false;
			message = msg_enter_valid_user_type;
		}

		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> isFirstNameValid(String firstName) {
		boolean isValid = false;
		String message = "";
		Pair<Boolean, String> firstNameNullOrEmptyPair = isNullOrEmpty(firstName);

		if (firstNameNullOrEmptyPair.getFirst()) {
			if (firstName.length() >= 2) {
				isValid = true;
			} else {
				isValid = false;
				message = msg_enter_valid_firstname;
			}
		} else {
			isValid = firstNameNullOrEmptyPair.getFirst();
			message = firstNameNullOrEmptyPair.getSecond();
		}
		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> isMiddleNameValid(String middleName) {
		boolean isValid = false;
		String message = "";
		Pair<Boolean, String> middleNameNullOrEmptyPair = isNullOrEmpty(middleName);

		if (middleNameNullOrEmptyPair.getFirst()) {
			if (middleName.length() >= 2) {
				isValid = true;
			} else {
				isValid = false;
				message = msg_enter_valid_middlename;
			}
		} else {
			isValid = middleNameNullOrEmptyPair.getFirst();
			message = middleNameNullOrEmptyPair.getSecond();
		}
		
		isValid = true;
		
		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> isLastNameValid(String lastName) {
		boolean isValid = false;
		String message = "";
		Pair<Boolean, String> lastNameNullOrEmptyPair = isNullOrEmpty(lastName);

		if (lastNameNullOrEmptyPair.getFirst()) {
			if (lastName.length() >= 2) {
				isValid = true;
			} else {
				isValid = false;
				message = msg_enter_valid_lastname;
			}
		} else {
			isValid = lastNameNullOrEmptyPair.getFirst();
			message = lastNameNullOrEmptyPair.getSecond();
		}
		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> isMaritalStatusValid(int maritalStatus) {
		boolean isValid = false;
		String message = "";

		if (maritalStatus > 0 && maritalStatus <= 5) {
			isValid = true;
		} else {
			isValid = false;
			message = msg_enter_valid_marital_status;
		}

		return Pair.of(isValid, message);
	}

	public Pair<Boolean, String> isGenderValid(int gender) {
		boolean isValid = false;
		String message = "";

		if (gender > 0 && gender <= 3) {
			isValid = true;
		} else {
			isValid = false;
			message = msg_enter_valid_gender;
		}

		return Pair.of(isValid, message);
	}

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

	public Pair<Boolean, String> isMobileValid(String mobile) {
		boolean isValid = false;
		String message = "";
		Pair<Boolean, String> mobileNullOrEmptyPair = isNullOrEmpty(mobile);

		if (mobileNullOrEmptyPair.getFirst()) {
			if (mobile.matches(mobileRegEx)) {
				isValid = true;
			} else {
				isValid = false;
				message = msg_enter_valid_mobile;
			}
		} else {
			isValid = mobileNullOrEmptyPair.getFirst();
			message = mobileNullOrEmptyPair.getSecond();
		}
		return Pair.of(isValid, message);
	}

}
