package com.hranalyticsapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hranalyticsapp.model.UsersMaster;
import com.hranalyticsapp.model.base.BaseResponse;
import com.hranalyticsapp.model.base.ErrorResponse;
import com.hranalyticsapp.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;

	@RequestMapping("/")
	@ResponseBody
	public BaseResponse<ArrayList<UsersMaster>> home() {
		BaseResponse<ArrayList<UsersMaster>> baseResponse = new BaseResponse<ArrayList<UsersMaster>>();
		ArrayList<UsersMaster> usersMaster = new ArrayList<UsersMaster>();
		usersMaster = (ArrayList<UsersMaster>) userRepo.findAll();
		if (usersMaster != null & !usersMaster.isEmpty()) {
			baseResponse.setData(usersMaster);
		} else {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorMessage("No users found");
		}
		return baseResponse;
	}

}
