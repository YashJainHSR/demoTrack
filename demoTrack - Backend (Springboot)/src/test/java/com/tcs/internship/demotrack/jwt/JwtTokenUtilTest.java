package com.tcs.internship.demotrack.jwt;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Clock;
import io.jsonwebtoken.impl.DefaultClock;

public class JwtTokenUtilTest {

	
	JwtTokenUtil jwtTokenUtilNew = new JwtTokenUtil("mySecret",(long)604800);	
	private Clock clock = DefaultClock.INSTANCE;
	
	@Test
	public void getUsernameFromTokenTest() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		assertEquals("1838900", jwtTokenUtilNew.getUsernameFromToken(token));
	}
	
	@Test
	public void getIssuedAtDateFromTokenTest() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		final Date createdDate = clock.now();
		assertEquals(DateFormat.getDateInstance(DateFormat.MEDIUM).format(createdDate), DateFormat.getDateInstance(DateFormat.MEDIUM).format(jwtTokenUtilNew.getIssuedAtDateFromToken(token)));
	}
	@Test
	public void getExpirationDateFromTokenTest() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		final Date createdDate = clock.now();
		final Date expirationDate = jwtTokenUtilNew.calculateExpirationDate(createdDate);
		assertEquals(DateFormat.getDateInstance(DateFormat.MEDIUM).format(expirationDate), DateFormat.getDateInstance(DateFormat.MEDIUM).format(jwtTokenUtilNew.getExpirationDateFromToken(token)));
	}
	@Test
	public void isTokenExpiredTest() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		assertEquals(false,jwtTokenUtilNew.isTokenExpired(token));
	}
	
	@Test
	public void ignoreTokenExpirationTest() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		assertEquals(false,jwtTokenUtilNew.ignoreTokenExpiration(token));
	}
	
	@Test
	public void validateTokenTest() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		assertEquals(jwtTokenUtilNew.validateToken(token, userDetails) ,jwtTokenUtilNew.validateToken(jwtTokenUtilNew.refreshToken(token), userDetails));
	}
	
	@Test
	public void validateTokenTest1() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		assertEquals(true ,jwtTokenUtilNew.validateToken(token, userDetails));
	}
	
	@Test
	public void validateTokenTest2() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		UserList.add(new JwtUserDetails((long) 1, "Yash Jain",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst1 = UserList.stream().filter(user -> user.getUsername().equals("Yash Jain"))
				.findFirst();
		UserDetails userDetails1 = findFirst1.get();
		assertEquals(false ,jwtTokenUtilNew.validateToken(token, userDetails1));
	}
	@Test
	public void canTokenBeRefreshedTest() {
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = jwtTokenUtilNew.generateToken(userDetails);
		assertEquals(true ,jwtTokenUtilNew.canTokenBeRefreshed(token));
	}
}
