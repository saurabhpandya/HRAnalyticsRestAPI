package com.hranalyticsapp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hranalyticsapp.model.UsersMster;

public interface UserRepository extends JpaRepository<UsersMster, Integer> {

}
