package com.hranalyticsapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hranalyticsapp.model.UsersMster;

@RestController
public class HomeController {

	@Autowired
	UserRepository userRepo;

	@RequestMapping("/")
	@ResponseBody
	public List<UsersMster> home() {
		System.out.println("Home Controller");
		return userRepo.findAll();
	}

}
