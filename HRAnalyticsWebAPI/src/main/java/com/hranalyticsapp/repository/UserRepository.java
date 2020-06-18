package com.hranalyticsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hranalyticsapp.model.UsersMaster;

public interface UserRepository extends JpaRepository<UsersMaster, Integer> {

	@Query(value = "SELECT * FROM USERS_MASTER WHERE email = ?1 AND active = 1 AND blocked = 0 AND deleted = 0", nativeQuery = true)
	UsersMaster validateEmail(String email);

	UsersMaster findByEmail(String email);

}
