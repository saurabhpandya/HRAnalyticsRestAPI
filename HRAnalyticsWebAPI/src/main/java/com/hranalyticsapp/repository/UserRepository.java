package com.hranalyticsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hranalyticsapp.model.UsersMaster;

public interface UserRepository extends JpaRepository<UsersMaster, Integer> {

}
