package com.tcs.internship.demotrack.controller;

import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.internship.demotrack.model.UserCredentials;
import com.tcs.internship.demotrack.model.DemoRequests;
import com.tcs.internship.demotrack.repository.UserCredentialsRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mail")
public class MailController {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private UserCredentialsRepository userCredentialsRepository;

	@PostMapping(path = "/newRequest")
	public ResponseEntity<?> sendNewRequestMail(@RequestBody String requestNumber) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		List<UserCredentials> to = userCredentialsRepository.findByRole("Approver");
		String subject = "New Demo Request";
		String messageBody = "";
		helper.setFrom(new InternetAddress("yashjain.2016@gmail.com", "demoTrack"));
		helper.setSubject(subject);
		try {
			for (int i = 0; i < to.size(); i++) {
				helper.setTo(to.get(i).getEmail());
				messageBody = "Dear " + to.get(i).getName() + "<br>A new Demo Request has been created with request# "
						+ requestNumber + ".<br/><br/>Please process the request as soon as possible.";
				helper.setText(messageBody, true);
				sender.send(message);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PostMapping(path = "/approval")
	public ResponseEntity<?> sendApprovalMail(@RequestBody @Valid DemoRequests request) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		List<UserCredentials> to = userCredentialsRepository.findByRole("Approver");
		to.remove(userCredentialsRepository.findByUsername(request.getAuthority()));
		String subject = "Demo Request - " + request.getStatus();
		String messageBody = "";
		try {
			helper.setTo(userCredentialsRepository.findByUsername(request.getRequesterUserId()).getEmail());
			helper.setFrom(new InternetAddress("yashjain.2016@gmail.com", "demoTrack"));
			helper.setSubject(subject);
			for (int i = 0; i < to.size(); i++) {
				helper.addCc(to.get(i).getEmail());
			}
			if ((request.getStatus()).equals("Approved")) {
				messageBody = "Dear " + userCredentialsRepository.findByUsername(request.getRequesterUserId()).getName()
						+ ",<br><br/>Your Demo Request (" + request.getRequestNumber()
						+ ") has been approved.<br/>Please proceed with the Demo.";
			} else {
				messageBody = "Dear " + userCredentialsRepository.findByUsername(request.getRequesterUserId()).getName()
						+ ",<br><br/>Your Demo Request #" + request.getRequestNumber() + " has been rejected.";
			}
			helper.setText(messageBody, true);
			sender.send(message);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
