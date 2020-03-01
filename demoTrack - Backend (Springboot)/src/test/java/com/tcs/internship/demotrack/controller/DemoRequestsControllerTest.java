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
import com.tcs.internship.demotrack.model.DemoRequests;
import com.tcs.internship.demotrack.repository.demoRequestsRepository;

@RunWith(MockitoJUnitRunner.class)
public class DemoRequestsControllerTest {

	@Mock
	demoRequestsRepository demoRequestsRepository;

	@Mock
	HttpServletResponse response;

	@Mock
	BindingResult result;

	@InjectMocks
	DemoRequestsController demoRequestsController;

	@Test
	public void getAllDemoRequestsTest() {
		List<DemoRequests> demoRequests = new ArrayList<DemoRequests>();
		demoRequests.add(new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId", "requesterName",
				"customerName", "customerEmail", "customerLocation", "customerBusinessLine", "contactPersonName",
				"contactPersonEmail", "productGroup", "productLine", "product", "demoSite", "demoDate", "demoSlot", 10,
				"demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5, "customerConvinced", "feedback",
				"comment", "status", "authority"));
		when(demoRequestsRepository.findAll()).thenReturn(demoRequests);
		demoRequestsController.getAllDemoRequests()
				.forEach((a) -> assertEquals(new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
						"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
						"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
						"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5,
						5, "customerConvinced", "feedback", "comment", "status", "authority"), a));
	}

	@Test
	public void getDemoRequestByUsernameTest() {
		List<DemoRequests> demoRequests = new ArrayList<DemoRequests>();
		demoRequests.add(new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId", "requesterName",
				"customerName", "customerEmail", "customerLocation", "customerBusinessLine", "contactPersonName",
				"contactPersonEmail", "productGroup", "productLine", "product", "demoSite", "demoDate", "demoSlot", 10,
				"demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5, "customerConvinced", "feedback",
				"comment", "status", "authority"));
		when(demoRequestsRepository.findByRequesterUserId(demoRequests.get(0).getRequesterUserId()))
				.thenReturn(demoRequests);
		demoRequestsController.getDemoRequestsByUsername("requesterUserId")
				.forEach((a) -> assertEquals(new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
						"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
						"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
						"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5,
						5, "customerConvinced", "feedback", "comment", "status", "authority"), a));
	}

	@Test
	public void getDemoRequestTest1() throws IOException {
		DemoRequests demoRequest = new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
				"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
				"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
				"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5,
				"customerConvinced", "feedback", "comment", "status", "authority");
		when(demoRequestsRepository.existsByRequestNumber(demoRequest.getRequestNumber())).thenReturn(false);
		assertEquals(null, demoRequestsController.getdemoRequest("000001", response));
	}

	@Test
	public void getDemoRequestTest2() throws IOException {
		DemoRequests demoRequest = new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
				"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
				"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
				"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5,
				"customerConvinced", "feedback", "comment", "status", "authority");
		when(demoRequestsRepository.existsByRequestNumber(demoRequest.getRequestNumber())).thenReturn(true);
		when(demoRequestsRepository.findByRequestNumber(demoRequest.getRequestNumber())).thenReturn(demoRequest);
		assertEquals(demoRequest, demoRequestsController.getdemoRequest("000001", response));
	}

	@Test
	public void addDemoRequestTest1() throws IOException {
		DemoRequests demoRequest = new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
				"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
				"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
				"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5,
				"customerConvinced", "feedback", "comment", "status", "authority");
		when(result.hasErrors()).thenReturn(true);
		List<FieldError> errors = new ArrayList<FieldError>();
		errors.add(new FieldError("error", "id", "sampleError"));
		when(result.getFieldErrors()).thenReturn(errors);
		assertEquals(null, demoRequestsController.addDemoRequests(demoRequest, result, response));
	}

	@Test
	public void addDemoRequestTest2() throws IOException {
		DemoRequests demoRequest = new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
				"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
				"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
				"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5,
				"customerConvinced", "feedback", "comment", "status", "authority");
		when(result.hasErrors()).thenReturn(false);
		when(demoRequestsRepository.save(demoRequest)).thenReturn(demoRequest);
		assertEquals(demoRequest, demoRequestsController.addDemoRequests(demoRequest, result, response));
	}

	@Test
	public void updateDemoRequestTest1() throws IOException {
		DemoRequests demoRequest = new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
				"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
				"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
				"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5,
				"customerConvinced", "feedback", "comment", "status", "authority");
		when(result.hasErrors()).thenReturn(true);
		List<FieldError> errors = new ArrayList<FieldError>();
		errors.add(new FieldError("error", "id", "sampleError"));
		when(result.getFieldErrors()).thenReturn(errors);
		assertEquals(null, demoRequestsController.updateDemoRequests(demoRequest, result, response));
	}

	@Test
	public void updateDemoRequestTest2() throws IOException {
		DemoRequests demoRequest = new DemoRequests((long) 1, "000001", "requestTimestamp", "requesterUserId",
				"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
				"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
				"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5, 5,
				"customerConvinced", "feedback", "comment", "status", "authority");
		when(result.hasErrors()).thenReturn(false);
		when(demoRequestsRepository.save(demoRequest)).thenReturn(demoRequest);
		assertEquals(demoRequest, demoRequestsController.updateDemoRequests(demoRequest, result, response));
	}
}
