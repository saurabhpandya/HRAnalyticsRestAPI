package com.hranalyticsapp.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hranalyticsapp.constants.Constants;
import com.hranalyticsapp.model.UsersMaster;
import com.hranalyticsapp.model.base.BaseResponse;
import com.hranalyticsapp.model.base.CommonResponse;
import com.hranalyticsapp.repository.UserRepository;
import com.hranalyticsapp.utility.Utility;
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
			baseResponse.setError(Utility.getErrorResponse(0, Constants.msg_no_user_found));
		}
		return baseResponse;
	}

	@GetMapping("users/{id}")
	@ResponseBody
	public BaseResponse<UsersMaster> getUserById(@PathVariable(name = "id") int id) {
		BaseResponse<UsersMaster> baseResponse = new BaseResponse<UsersMaster>();
		try {
			if (id > 0) {
				Optional<UsersMaster> userMasterOptional = userRepo.findById(id);
				if (userMasterOptional.isPresent()) {
					UsersMaster userMaster = new UsersMaster();
					userMaster = userMasterOptional.get();
					if (userMaster != null) {
						baseResponse.setData(userMaster);
					} else {
						baseResponse.setError(Utility.getErrorResponse(0, Constants.msg_no_user_found));
					}
				} else {
					baseResponse.setError(Utility.getErrorResponse(0, Constants.msg_no_user_found));
				}
			} else {
				baseResponse.setError(Utility.getErrorResponse(0, Constants.msg_enter_valid_data));
			}
		} catch (Exception e) {
			e.printStackTrace();
			baseResponse.setError(Utility.getErrorResponse(0, e.getLocalizedMessage()));
		}

		return baseResponse;
	}

	@DeleteMapping("users/{id}")
	@ResponseBody
	public BaseResponse<CommonResponse> deleteUserById(@PathVariable(name = "id") int id) {
		BaseResponse<CommonResponse> baseResponse = new BaseResponse<CommonResponse>();
		try {
			if (id > 0) {
				Optional<UsersMaster> userMasterOptional = userRepo.findById(id);
				if (userMasterOptional.isPresent()) {
					UsersMaster markDeletedUserMaster = new UsersMaster();
					markDeletedUserMaster = userMasterOptional.get();
					markDeletedUserMaster.setActive(false);
					markDeletedUserMaster.setDeleted(true);
					markDeletedUserMaster.setBlocked(false);
					userRepo.save(markDeletedUserMaster);
					CommonResponse deletedUserResponse = new CommonResponse();
					deletedUserResponse.setMessage(Constants.msg_user_deleted);
					baseResponse.setData(deletedUserResponse);
				} else {
					baseResponse.setError(Utility.getErrorResponse(0, Constants.msg_no_user_found));
				}
			} else {
				baseResponse.setError(Utility.getErrorResponse(0, Constants.msg_enter_valid_data));
			}
		} catch (Exception e) {
			e.printStackTrace();
			baseResponse.setError(Utility.getErrorResponse(0, e.getLocalizedMessage()));
		}
		return baseResponse;
	}

	@PostMapping("users/deActivate")
	@ResponseBody
	public BaseResponse<CommonResponse> deActivateUser(@RequestBody UsersMaster activateUserRequest) {
		BaseResponse<CommonResponse> activatedResponse = new BaseResponse<CommonResponse>();
		UsersMaster usersMaster = new UsersMaster();
		if (activateUserRequest != null) {
			UsersValidator usersValidator = new UsersValidator();
			Pair<Boolean, String> emailUserValidatorPair = usersValidator.isEmailValid(activateUserRequest.getEmail());
			if (emailUserValidatorPair.getFirst()) {
				Pair<Boolean, String> passwordUserValidatorPair = usersValidator
						.isPasswordValid(activateUserRequest.getPassword());
				if (passwordUserValidatorPair.getFirst()) {
					usersMaster = userRepo.findByEmail(activateUserRequest.getEmail());
					if (usersMaster != null) {

						Pair<Boolean, String> validatePasswordPair = usersValidator
								.validatePassword(activateUserRequest.getPassword(), usersMaster.getPassword());
						if (validatePasswordPair.getFirst()) {
							usersMaster.setActive(false);
							usersMaster.setBlocked(false);
							usersMaster.setDeleted(true);
							userRepo.save(usersMaster);
							CommonResponse activatedCommonResponse = new CommonResponse();
							activatedCommonResponse.setMessage(Constants.msg_user_deactivated);
							activatedResponse.setData(activatedCommonResponse);
						} else {
							activatedResponse.setError(Utility.getErrorResponse(0, validatePasswordPair.getSecond()));
						}
					} else {
						activatedResponse.setError(Utility.getErrorResponse(0, Constants.msg_no_user_found));
					}
				} else {
					activatedResponse.setError(Utility.getErrorResponse(0, passwordUserValidatorPair.getSecond()));
				}
			} else {
				activatedResponse.setError(Utility.getErrorResponse(0, emailUserValidatorPair.getSecond()));
			}
		} else {
			activatedResponse.setError(Utility.getErrorResponse(0, Constants.msg_enter_valid_data));
		}
		return activatedResponse;
	}

	@PostMapping("users/activate")
	@ResponseBody
	public BaseResponse<CommonResponse> activateUser(@RequestBody UsersMaster activateUserRequest) {
		BaseResponse<CommonResponse> activatedResponse = new BaseResponse<CommonResponse>();
		UsersMaster usersMaster = new UsersMaster();
		if (activateUserRequest != null) {
			UsersValidator usersValidator = new UsersValidator();
			Pair<Boolean, String> emailUserValidatorPair = usersValidator.isEmailValid(activateUserRequest.getEmail());
			if (emailUserValidatorPair.getFirst()) {
				Pair<Boolean, String> passwordUserValidatorPair = usersValidator
						.isPasswordValid(activateUserRequest.getPassword());
				if (passwordUserValidatorPair.getFirst()) {
					usersMaster = userRepo.findByEmail(activateUserRequest.getEmail());
					if (usersMaster != null) {
						usersMaster.setActive(true);
						usersMaster.setBlocked(false);
						usersMaster.setDeleted(false);
						userRepo.save(usersMaster);
						CommonResponse activatedCommonResponse = new CommonResponse();
						activatedCommonResponse.setMessage(Constants.msg_user_activated);
						activatedResponse.setData(activatedCommonResponse);
					} else {
						activatedResponse.setError(Utility.getErrorResponse(0, Constants.msg_no_user_found));
					}
				} else {
					activatedResponse.setError(Utility.getErrorResponse(0, passwordUserValidatorPair.getSecond()));
				}
			} else {
				activatedResponse.setError(Utility.getErrorResponse(0, emailUserValidatorPair.getSecond()));
			}
		} else {
			activatedResponse.setError(Utility.getErrorResponse(0, Constants.msg_enter_valid_data));
		}
		return activatedResponse;
	}

	@PostMapping("users/validate")
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
					usersMaster = userRepo.validateEmail(validateUserRequest.getEmail());
					if (usersMaster != null) {
						Pair<Boolean, String> validatePasswordPair = usersValidator
								.validatePassword(validateUserRequest.getPassword(), usersMaster.getPassword());
						if (validatePasswordPair.getFirst()) {
							validateUserResponse.setData(usersMaster);
						} else {
							validateUserResponse
									.setError(Utility.getErrorResponse(0, validatePasswordPair.getSecond()));
						}
					} else {
						validateUserResponse.setError(Utility.getErrorResponse(0, Constants.msg_no_user_found));
					}
				} else {
					validateUserResponse.setError(Utility.getErrorResponse(0, passwordUserValidatorPair.getSecond()));
				}
			} else {
				validateUserResponse.setError(Utility.getErrorResponse(0, emailUserValidatorPair.getSecond()));
			}

		} else {
			validateUserResponse.setError(Utility.getErrorResponse(0, Constants.msg_enter_valid_data));
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
															validateUserResponse.setError(Utility.getErrorResponse(0,
																	Constants.msg_user_exist));
														}
													} catch (Exception e) {
														e.printStackTrace();
														validateUserResponse.setError(
																Utility.getErrorResponse(0, e.getLocalizedMessage()));
													}
												}
											} else {
												validateUserResponse.setError(Utility.getErrorResponse(0,
														passwordUserValidatorPair.getSecond()));
											}
										} else {
											validateUserResponse.setError(
													Utility.getErrorResponse(0, emailUserValidatorPair.getSecond()));
										}

									} else {
										validateUserResponse
												.setError(Utility.getErrorResponse(0, validateGenderPair.getSecond()));
									}
								} else {
									validateUserResponse.setError(
											Utility.getErrorResponse(0, validateMaritalStatusPair.getSecond()));
								}
							} else {
								validateUserResponse
										.setError(Utility.getErrorResponse(0, validateLastNamePair.getSecond()));
							}
						} else {
							validateUserResponse
									.setError(Utility.getErrorResponse(0, validateMiddleNamePair.getSecond()));
						}
					} else {
						validateUserResponse.setError(Utility.getErrorResponse(0, validateFirstNamePair.getSecond()));
					}
				} else {
					validateUserResponse.setError(Utility.getErrorResponse(0, validateUserTypePair.getSecond()));
				}

			} else {
				validateUserResponse.setError(Utility.getErrorResponse(0, validatePrefixPair.getSecond()));
			}

		} else {
			validateUserResponse.setError(Utility.getErrorResponse(0, Constants.msg_enter_valid_data));
		}
		return validateUserResponse;
	}

	@PutMapping("users")
	@ResponseBody
	public BaseResponse<UsersMaster> updateUser(@RequestBody UsersMaster updateUserRequest) {
		BaseResponse<UsersMaster> validateUserResponse = new BaseResponse<UsersMaster>();
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
														UsersMaster usersMaster = new UsersMaster();
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
																	validateUserResponse
																			.setError(Utility.getErrorResponse(0,
																					Constants.msg_email_exist));
																}
															} else {
																userRepo.save(updateUserRequest);
																usersMaster = userRepo
																		.findById(updateUserRequest.getId()).get();
																validateUserResponse.setData(usersMaster);
															}
														} else {
															validateUserResponse.setError(Utility.getErrorResponse(0,
																	Constants.msg_no_user_found));
														}
													} catch (Exception e) {
														e.printStackTrace();
														validateUserResponse.setError(
																Utility.getErrorResponse(0, e.getLocalizedMessage()));
													}
												} else {
													validateUserResponse.setError(
															Utility.getErrorResponse(0, Constants.msg_no_user_found));
												}
											} else {
												validateUserResponse.setError(Utility.getErrorResponse(0,
														passwordUserValidatorPair.getSecond()));
											}
										} else {
											validateUserResponse.setError(
													Utility.getErrorResponse(0, emailUserValidatorPair.getSecond()));
										}
									} else {
										validateUserResponse
												.setError(Utility.getErrorResponse(0, validateGenderPair.getSecond()));
									}
								} else {
									validateUserResponse.setError(
											Utility.getErrorResponse(0, validateMaritalStatusPair.getSecond()));
								}
							} else {
								validateUserResponse
										.setError(Utility.getErrorResponse(0, validateLastNamePair.getSecond()));
							}
						} else {
							validateUserResponse
									.setError(Utility.getErrorResponse(0, validateMiddleNamePair.getSecond()));
						}
					} else {
						validateUserResponse.setError(Utility.getErrorResponse(0, validateFirstNamePair.getSecond()));
					}
				} else {
					validateUserResponse.setError(Utility.getErrorResponse(0, validateUserTypePair.getSecond()));
				}
			} else {
				validateUserResponse.setError(Utility.getErrorResponse(0, validatePrefixPair.getSecond()));
			}
		} else {
			validateUserResponse.setError(Utility.getErrorResponse(0, Constants.msg_enter_valid_data));
		}
		return validateUserResponse;
	}

}
