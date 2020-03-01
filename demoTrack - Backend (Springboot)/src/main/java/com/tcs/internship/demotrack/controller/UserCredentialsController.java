package com.tcs.internship.demotrack.controller;

import com.tcs.internship.demotrack.decryptor.Decrypter;
import com.tcs.internship.demotrack.model.UserCredentials;
import com.tcs.internship.demotrack.repository.UserCredentialsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/userDetails")
public class UserCredentialsController {

	@Autowired
	private UserCredentialsRepository userCredentialsRepository;

	private Decrypter decrypter = new Decrypter();

	@GetMapping(path = "")
	public @ResponseBody Iterable<UserCredentials> getAllUserCredentials() {
		return userCredentialsRepository.findAll();
	}

	@GetMapping(path = "/{username}")
	public @ResponseBody UserCredentials getUserCredential(@PathVariable String username, HttpServletResponse response)
			throws IOException {
		if (!userCredentialsRepository.existsByUsername(username)) {
			response.sendError(404, "User Not Found");
			return null;
		}
		UserCredentials user = userCredentialsRepository.findByUsername(username);
		user.setPassword("");
		return user;
	}

	@PostMapping(path = "")
	public @ResponseBody UserCredentials addUserCredential(@RequestBody @Valid UserCredentials userCred,
			BindingResult validationResult, HttpServletResponse response) throws IOException {
		if (validationResult.hasErrors()) {
			String err = "";
			List<FieldError> errors = validationResult.getFieldErrors();
			for (FieldError error : errors) {
				err += error.getDefaultMessage();
			}
			// response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(400, err);
			return null;
		}

		if (userCredentialsRepository.existsByUsername(userCred.getUsername())) {
			response.sendError(400, "User Already Exists");
			return null;
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userCred.setPassword(decrypter.decrypt(userCred.getPassword()));
			userCred.setPassword(encoder.encode(userCred.getPassword()));
			response.setStatus(201);
			UserCredentials user = userCredentialsRepository.save(userCred);
			user.setPassword("");
			return user;
		}
	}
}
