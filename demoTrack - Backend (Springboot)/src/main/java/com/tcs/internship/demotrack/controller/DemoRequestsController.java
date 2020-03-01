package com.tcs.internship.demotrack.controller;

import com.tcs.internship.demotrack.model.DemoRequests;
import com.tcs.internship.demotrack.repository.demoRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/demoRequests")
public class DemoRequestsController {

	@Autowired
	private demoRequestsRepository demoRequestsRepository;

	@GetMapping(path = "")
	public @ResponseBody Iterable<DemoRequests> getAllDemoRequests() {
		return demoRequestsRepository.findAll();
	}

	@GetMapping(path = "/user/{requesterUserId}")
	public @ResponseBody Iterable<DemoRequests> getDemoRequestsByUsername(@PathVariable String requesterUserId) {
		return demoRequestsRepository.findByRequesterUserId(requesterUserId);
	}

	@GetMapping(path = "/{requestNumber}")
	public @ResponseBody DemoRequests getdemoRequest(@PathVariable String requestNumber, HttpServletResponse response)
			throws IOException {
		if (!demoRequestsRepository.existsByRequestNumber(requestNumber)) {
			response.sendError(404, "Request Not Found");
			return null;
		}
		return demoRequestsRepository.findByRequestNumber(requestNumber);
	}

	@PostMapping(path = "")
	public @ResponseBody DemoRequests addDemoRequests(@RequestBody @Valid DemoRequests demoRequest,
			BindingResult validationResult, HttpServletResponse response) throws IOException {
		if (validationResult.hasErrors()) {
			String err = "";
			List<FieldError> errors = validationResult.getFieldErrors();
			for (FieldError error : errors) {
				err += error.getDefaultMessage();
			}
			response.sendError(400, err);
			return null;
		}
		response.setStatus(201);
		DemoRequests newRequest = demoRequestsRepository.save(demoRequest);
		newRequest.setRequestNumber(String.format("%06d", newRequest.getId()));
		return demoRequestsRepository.save(demoRequest);
	}

	@PutMapping(path = "")
	public @ResponseBody DemoRequests updateDemoRequests(@RequestBody @Valid DemoRequests demoRequest,
			BindingResult validationResult, HttpServletResponse response) throws IOException {
		if (validationResult.hasErrors()) {
			String err = "";
			List<FieldError> errors = validationResult.getFieldErrors();
			for (FieldError error : errors) {
				err += error.getDefaultMessage();
			}
			response.sendError(400, err);
			return null;
		}
		response.setStatus(200);
		return demoRequestsRepository.save(demoRequest);
	}

}
