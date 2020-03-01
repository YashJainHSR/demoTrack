package com.tcs.internship.demotrack.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.tcs.internship.demotrack.decryptor.Decrypter;
import com.tcs.internship.demotrack.model.UserCredentials;
import com.tcs.internship.demotrack.repository.UserCredentialsRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@Mock
	HttpServletResponse response;

	@Mock
	BindingResult result;

	@Mock
	Decrypter decrypter;

	@InjectMocks
	UserCredentialsController userCredentialsController;

	@Test
	public void getAllUserCredentialsTest() {
		List<UserCredentials> userCredentials = new ArrayList<UserCredentials>();
		userCredentials.add(new UserCredentials((long) 1, "1838900", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Admin", ""));
		when(userCredentialsRepository.findAll()).thenReturn(userCredentials);
		userCredentialsController.getAllUserCredentials().forEach((a) -> assertEquals(new UserCredentials((long) 1,
				"1838900", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Admin", ""), a));
	}

	@Test
	public void getUserCredentialTest1() throws IOException {
		UserCredentials userCredentials = new UserCredentials((long) 1, "1838900", "EHzocMRbwMEA54vyB9GZPg",
				"Yash Jain", "yashjain0898@gmail.com", "Admin", "");
		when(userCredentialsRepository.existsByUsername("1838900")).thenReturn(true);
		when(userCredentialsRepository.findByUsername("1838900")).thenReturn(userCredentials);
		assertEquals(userCredentials, userCredentialsController.getUserCredential("1838900", response));
	}

	@Test
	public void getUserCredentialTest2() throws IOException {
		when(userCredentialsRepository.existsByUsername("1838900")).thenReturn(false);
		assertEquals(null, userCredentialsController.getUserCredential("1838900", response));
	}

	@Test
	public void addUserCredentialTest1() throws IOException {
		UserCredentials userCredentials = new UserCredentials((long) 1, "1838900", "EHzocMRbwMEA54vyB9GZPg",
				"Yash Jain", "yashjain0898@gmail.com", "Admin", "");
		when(result.hasErrors()).thenReturn(true);
		List<FieldError> errors = new ArrayList<FieldError>();
		errors.add(new FieldError("error", "id", "sampleError"));
		when(result.getFieldErrors()).thenReturn(errors);
		assertEquals(null, userCredentialsController.addUserCredential(userCredentials, result, response));
	}

	@Test
	public void addUserCredentialTest2() throws IOException {
		UserCredentials userCredentials = new UserCredentials((long) 1, "1838900", "EHzocMRbwMEA54vyB9GZPg",
				"Yash Jain", "yashjain0898@gmail.com", "Admin", "");
		when(result.hasErrors()).thenReturn(false);
		when(userCredentialsRepository.existsByUsername("1838900")).thenReturn(true);
		assertEquals(null, userCredentialsController.addUserCredential(userCredentials, result, response));
	}

	@Test
	public void addUserCredentialTest3() throws IOException {
		UserCredentials userCredentials = new UserCredentials((long) 1, "1838900", "EHzocMRbwMEA54vyB9GZPg",
				"Yash Jain", "yashjain0898@gmail.com", "Admin", "");
		when(userCredentialsRepository.existsByUsername("1838900")).thenReturn(false);
		when(result.hasErrors()).thenReturn(false);
		when(decrypter.decrypt("EHzocMRbwMEA54vyB9GZPg")).thenReturn("Pwd@2020");
		when(userCredentialsRepository.save(userCredentials)).thenReturn(userCredentials);
		assertEquals(userCredentials, userCredentialsController.addUserCredential(userCredentials, result, response));
	}

}
