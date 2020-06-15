package com.hranalyticsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hranalyticsapp.model.UsersMaster;
import com.hranalyticsapp.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;

	@RequestMapping("/")
	@ResponseBody
	public List<UsersMaster> home() {
		return userRepo.findAll();
	}

}
