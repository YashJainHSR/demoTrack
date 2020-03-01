package com.tcs.internship.demotrack.repository;

import com.tcs.internship.demotrack.model.UserCredentials;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {

	UserCredentials findByUsername(String username);

	Boolean existsByUsername(String username);

	List<UserCredentials> findByRole(String role);
}
