package com.hranalyticsapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hranalyticsapp.constants.Constants;
import com.hranalyticsapp.model.UsersMaster;
import com.hranalyticsapp.model.base.BaseResponse;
import com.hranalyticsapp.model.base.ErrorResponse;
import com.hranalyticsapp.repository.UserRepository;
import com.hranalyticsapp.validator.UsersValidator;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;

	@GetMapping("users")
	@ResponseBody
	public BaseResponse<ArrayList<UsersMaster>> getUsers() {
		BaseResponse<ArrayList<UsersMaster>> baseResponse = new BaseResponse<ArrayList<UsersMaster>>();
		ArrayList<UsersMaster> usersMaster = new ArrayList<UsersMaster>();
		usersMaster = (ArrayList<UsersMaster>) userRepo.findAll();
		if (usersMaster != null & !usersMaster.isEmpty()) {
			baseResponse.setData(usersMaster);
		} else {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorMessage(Constants.msg_no_user_found);
			baseResponse.setError(errorResponse);
		}
		return baseResponse;
	}

	@PostMapping("validateUser")
	@ResponseBody
	public BaseResponse<UsersMaster> validateUser(@RequestBody UsersMaster validateUserRequest) {
		BaseResponse<UsersMaster> validateUserResponse = new BaseResponse<UsersMaster>();
		UsersMaster usersMaster = new UsersMaster();
		if (validateUserRequest != null) {
			UsersValidator usersValidator = new UsersValidator();
			Pair<Boolean, String> emailUserValidatorPair = usersValidator.isEmailValid(validateUserRequest.getEmail());
			Pair<Boolean, String> passwordUserValidatorPair = usersValidator
					.isPasswordValid(validateUserRequest.getPassword());
			if (emailUserValidatorPair.getFirst()) {
				if (passwordUserValidatorPair.getFirst()) {
					usersMaster = userRepo.findByEmail(validateUserRequest.getEmail());
					if (usersMaster != null) {
						Pair<Boolean, String> validatePasswordPair = usersValidator
								.validatePassword(validateUserRequest.getPassword(), usersMaster.getPassword());
						if (validatePasswordPair.getFirst()) {
							validateUserResponse.setData(usersMaster);
						} else {
							ErrorResponse errorResponse = new ErrorResponse();
							errorResponse.setErrorMessage(validatePasswordPair.getSecond());
							validateUserResponse.setError(errorResponse);
						}
					} else {
						ErrorResponse errorResponse = new ErrorResponse();
						errorResponse.setErrorMessage(Constants.msg_no_user_found);
						validateUserResponse.setError(errorResponse);
					}
				} else {
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setErrorMessage(passwordUserValidatorPair.getSecond());
					validateUserResponse.setError(errorResponse);
				}
			} else {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrorMessage(emailUserValidatorPair.getSecond());
				validateUserResponse.setError(errorResponse);
			}

		} else {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorMessage(Constants.msg_enter_valid_data);
			validateUserResponse.setError(errorResponse);
		}
		return validateUserResponse;
	}

}
