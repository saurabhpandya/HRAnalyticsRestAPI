package com.hranalyticsapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
			if (emailUserValidatorPair.getFirst()) {
				Pair<Boolean, String> passwordUserValidatorPair = usersValidator
						.isPasswordValid(validateUserRequest.getPassword());
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

	@PostMapping("users")
	@ResponseBody
	public BaseResponse<UsersMaster> addUser(@RequestBody UsersMaster addUserRequest) {
		BaseResponse<UsersMaster> validateUserResponse = new BaseResponse<UsersMaster>();
		UsersMaster usersMaster = new UsersMaster();
		if (addUserRequest != null) {
			UsersValidator usersValidator = new UsersValidator();

			Pair<Boolean, String> validatePrefixPair = usersValidator.isPrefixValid(addUserRequest.getPrefix());
			if (validatePrefixPair.getFirst()) {
				Pair<Boolean, String> validateUserTypePair = usersValidator
						.isUserTypeValid(addUserRequest.getUserType());
				if (validateUserTypePair.getFirst()) {
					Pair<Boolean, String> validateFirstNamePair = usersValidator
							.isFirstNameValid(addUserRequest.getFirstName());
					if (validateFirstNamePair.getFirst()) {
						Pair<Boolean, String> validateMiddleNamePair = usersValidator
								.isMiddleNameValid(addUserRequest.getMiddleName());
						if (validateMiddleNamePair.getFirst()) {
							Pair<Boolean, String> validateLastNamePair = usersValidator
									.isLastNameValid(addUserRequest.getLastName());
							if (validateLastNamePair.getFirst()) {
								Pair<Boolean, String> validateMaritalStatusPair = usersValidator
										.isMaritalStatusValid(addUserRequest.getMaritalStatus());
								if (validateMaritalStatusPair.getFirst()) {
									Pair<Boolean, String> validateGenderPair = usersValidator
											.isGenderValid(addUserRequest.getGender());
									if (validateGenderPair.getFirst()) {
										Pair<Boolean, String> emailUserValidatorPair = usersValidator
												.isEmailValid(addUserRequest.getEmail());
										if (emailUserValidatorPair.getFirst()) {
											Pair<Boolean, String> passwordUserValidatorPair = usersValidator
													.isPasswordValid(addUserRequest.getPassword());
											if (passwordUserValidatorPair.getFirst()) {
												if (addUserRequest.getId() == 0) {
													try {
														usersMaster = userRepo.findByEmail(addUserRequest.getEmail());
														if (usersMaster == null) {
															userRepo.save(addUserRequest);
															usersMaster = userRepo
																	.findByEmail(addUserRequest.getEmail());
															validateUserResponse.setData(usersMaster);
														} else {
															ErrorResponse errorResponse = new ErrorResponse();
															errorResponse.setErrorMessage(Constants.msg_user_exist);
															validateUserResponse.setError(errorResponse);
														}
													} catch (Exception e) {
														e.printStackTrace();
														ErrorResponse errorResponse = new ErrorResponse();
														errorResponse.setErrorMessage(e.getLocalizedMessage());
														validateUserResponse.setError(errorResponse);
													}
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
										errorResponse.setErrorMessage(validateGenderPair.getSecond());
										validateUserResponse.setError(errorResponse);
									}
								} else {
									ErrorResponse errorResponse = new ErrorResponse();
									errorResponse.setErrorMessage(validateMaritalStatusPair.getSecond());
									validateUserResponse.setError(errorResponse);
								}
							} else {
								ErrorResponse errorResponse = new ErrorResponse();
								errorResponse.setErrorMessage(validateLastNamePair.getSecond());
								validateUserResponse.setError(errorResponse);
							}
						} else {
							ErrorResponse errorResponse = new ErrorResponse();
							errorResponse.setErrorMessage(validateMiddleNamePair.getSecond());
							validateUserResponse.setError(errorResponse);
						}
					} else {
						ErrorResponse errorResponse = new ErrorResponse();
						errorResponse.setErrorMessage(validateFirstNamePair.getSecond());
						validateUserResponse.setError(errorResponse);
					}
				} else {
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setErrorMessage(validateUserTypePair.getSecond());
					validateUserResponse.setError(errorResponse);
				}

			} else {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrorMessage(validatePrefixPair.getSecond());
				validateUserResponse.setError(errorResponse);
			}

		} else {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorMessage(Constants.msg_enter_valid_data);
			validateUserResponse.setError(errorResponse);
		}
		return validateUserResponse;
	}

	@PutMapping("users")
	@ResponseBody
	public BaseResponse<UsersMaster> updateUser(@RequestBody UsersMaster updateUserRequest) {
		BaseResponse<UsersMaster> validateUserResponse = new BaseResponse<UsersMaster>();
		UsersMaster usersMaster = new UsersMaster();
		if (updateUserRequest != null) {
			UsersValidator usersValidator = new UsersValidator();

			Pair<Boolean, String> validatePrefixPair = usersValidator.isPrefixValid(updateUserRequest.getPrefix());
			if (validatePrefixPair.getFirst()) {
				Pair<Boolean, String> validateUserTypePair = usersValidator
						.isUserTypeValid(updateUserRequest.getUserType());
				if (validateUserTypePair.getFirst()) {
					Pair<Boolean, String> validateFirstNamePair = usersValidator
							.isFirstNameValid(updateUserRequest.getFirstName());
					if (validateFirstNamePair.getFirst()) {
						Pair<Boolean, String> validateMiddleNamePair = usersValidator
								.isMiddleNameValid(updateUserRequest.getMiddleName());
						if (validateMiddleNamePair.getFirst()) {
							Pair<Boolean, String> validateLastNamePair = usersValidator
									.isLastNameValid(updateUserRequest.getLastName());
							if (validateLastNamePair.getFirst()) {
								Pair<Boolean, String> validateMaritalStatusPair = usersValidator
										.isMaritalStatusValid(updateUserRequest.getMaritalStatus());
								if (validateMaritalStatusPair.getFirst()) {
									Pair<Boolean, String> validateGenderPair = usersValidator
											.isGenderValid(updateUserRequest.getGender());
									if (validateGenderPair.getFirst()) {
										Pair<Boolean, String> emailUserValidatorPair = usersValidator
												.isEmailValid(updateUserRequest.getEmail());
										if (emailUserValidatorPair.getFirst()) {
											Pair<Boolean, String> passwordUserValidatorPair = usersValidator
													.isPasswordValid(updateUserRequest.getPassword());
											if (passwordUserValidatorPair.getFirst()) {

												if (updateUserRequest.getId() > 0) {
													try {
														usersMaster = userRepo.findById(updateUserRequest.getId())
																.get();
														UsersMaster userMasterByEmail = userRepo
																.findByEmail(updateUserRequest.getEmail());
														if (usersMaster != null) {
															if (userMasterByEmail != null) {
																if (userMasterByEmail.getId() == updateUserRequest
																		.getId()) {
																	userRepo.save(updateUserRequest);
																	usersMaster = userRepo
																			.findById(updateUserRequest.getId()).get();
																	validateUserResponse.setData(usersMaster);
																} else {
																	ErrorResponse errorResponse = new ErrorResponse();
																	errorResponse
																			.setErrorMessage(Constants.msg_email_exist);
																	validateUserResponse.setError(errorResponse);
																}
															} else {
																userRepo.save(updateUserRequest);
																usersMaster = userRepo
																		.findById(updateUserRequest.getId()).get();
																validateUserResponse.setData(usersMaster);
															}
														} else {
															ErrorResponse errorResponse = new ErrorResponse();
															errorResponse.setErrorMessage(Constants.msg_no_user_found);
															validateUserResponse.setError(errorResponse);
														}
													} catch (Exception e) {
														e.printStackTrace();
														ErrorResponse errorResponse = new ErrorResponse();
														errorResponse.setErrorMessage(e.getLocalizedMessage());
														validateUserResponse.setError(errorResponse);
													}
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
										errorResponse.setErrorMessage(validateGenderPair.getSecond());
										validateUserResponse.setError(errorResponse);
									}
								} else {
									ErrorResponse errorResponse = new ErrorResponse();
									errorResponse.setErrorMessage(validateMaritalStatusPair.getSecond());
									validateUserResponse.setError(errorResponse);
								}
							} else {
								ErrorResponse errorResponse = new ErrorResponse();
								errorResponse.setErrorMessage(validateLastNamePair.getSecond());
								validateUserResponse.setError(errorResponse);
							}
						} else {
							ErrorResponse errorResponse = new ErrorResponse();
							errorResponse.setErrorMessage(validateMiddleNamePair.getSecond());
							validateUserResponse.setError(errorResponse);
						}
					} else {
						ErrorResponse errorResponse = new ErrorResponse();
						errorResponse.setErrorMessage(validateFirstNamePair.getSecond());
						validateUserResponse.setError(errorResponse);
					}
				} else {
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setErrorMessage(validateUserTypePair.getSecond());
					validateUserResponse.setError(errorResponse);
				}

			} else {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrorMessage(validatePrefixPair.getSecond());
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
