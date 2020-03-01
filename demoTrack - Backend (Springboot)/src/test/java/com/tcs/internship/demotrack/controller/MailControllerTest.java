package com.tcs.internship.demotrack.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import com.tcs.internship.demotrack.model.DemoRequests;
import com.tcs.internship.demotrack.model.UserCredentials;
import com.tcs.internship.demotrack.repository.UserCredentialsRepository;

@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {

	@Mock
	JavaMailSender sender;

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@InjectMocks
	MailController mailController;

	@Test
	public void sendNewRequestMailTest1() throws Exception {
		when(sender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		List<UserCredentials> userCredentials = new ArrayList<UserCredentials>();
		userCredentials.add(new UserCredentials((long) 1, "1838900", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByRole("Approver")).thenReturn(userCredentials);
		Mockito.doThrow(new RuntimeException()).when(sender).send(Mockito.any(MimeMessage.class));
		ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		assertEquals(r, mailController.sendNewRequestMail("1838900"));
	}

	@Test
	public void sendNewRequestMailTest2() throws Exception {
		when(sender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		List<UserCredentials> userCredentials = new ArrayList<UserCredentials>();
		userCredentials.add(new UserCredentials((long) 1, "1838900", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByRole("Approver")).thenReturn(userCredentials);
		ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
		assertEquals(r, mailController.sendNewRequestMail("1838900"));
	}

	@Test
	public void sendApprovalMailTest1() throws Exception {
		when(sender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		List<UserCredentials> userCredentials = new ArrayList<UserCredentials>();
		userCredentials.add(new UserCredentials((long) 1, "approver", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		userCredentials.add(new UserCredentials((long) 1, "approver2", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByRole("Approver")).thenReturn(userCredentials);
		when(userCredentialsRepository.findByUsername("approver")).thenReturn(new UserCredentials((long) 1, "approver",
				"EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByUsername("1838900")).thenReturn(new UserCredentials((long) 1, "1838900",
				"EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Sales", ""));
		Mockito.doThrow(new RuntimeException()).when(sender).send(Mockito.any(MimeMessage.class));
		ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		assertEquals(r,
				mailController.sendApprovalMail(new DemoRequests((long) 1, "000001", "requestTimestamp", "1838900",
						"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
						"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
						"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5,
						5, "customerConvinced", "feedback", "comment", "status", "approver")));
	}

	@Test
	public void sendApprovalMailTest2() throws Exception {
		when(sender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		List<UserCredentials> userCredentials = new ArrayList<UserCredentials>();
		userCredentials.add(new UserCredentials((long) 1, "approver", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		userCredentials.add(new UserCredentials((long) 1, "approver2", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByRole("Approver")).thenReturn(userCredentials);
		when(userCredentialsRepository.findByUsername("approver")).thenReturn(new UserCredentials((long) 1, "approver",
				"EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByUsername("1838900")).thenReturn(new UserCredentials((long) 1, "1838900",
				"EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Sales", ""));
		ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
		assertEquals(r,
				mailController.sendApprovalMail(new DemoRequests((long) 1, "000001", "requestTimestamp", "1838900",
						"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
						"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
						"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5,
						5, "customerConvinced", "feedback", "comment", "status", "approver")));
	}

	@Test
	public void sendApprovalMailTest3() throws Exception {
		when(sender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		List<UserCredentials> userCredentials = new ArrayList<UserCredentials>();
		userCredentials.add(new UserCredentials((long) 1, "approver", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		userCredentials.add(new UserCredentials((long) 1, "approver2", "EHzocMRbwMEA54vyB9GZPg", "Yash Jain",
				"yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByRole("Approver")).thenReturn(userCredentials);
		when(userCredentialsRepository.findByUsername("approver")).thenReturn(new UserCredentials((long) 1, "approver",
				"EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Approver", ""));
		when(userCredentialsRepository.findByUsername("1838900")).thenReturn(new UserCredentials((long) 1, "1838900",
				"EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Sales", ""));
		ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
		assertEquals(r,
				mailController.sendApprovalMail(new DemoRequests((long) 1, "000001", "requestTimestamp", "1838900",
						"requesterName", "customerName", "customerEmail", "customerLocation", "customerBusinessLine",
						"contactPersonName", "contactPersonEmail", "productGroup", "productLine", "product", "demoSite",
						"demoDate", "demoSlot", 10, "demoExpectation", "aseDressCode", 5, 5, "demoSitePrepared", 5, 5,
						5, "customerConvinced", "feedback", "comment", "Approved", "approver")));
	}
}
