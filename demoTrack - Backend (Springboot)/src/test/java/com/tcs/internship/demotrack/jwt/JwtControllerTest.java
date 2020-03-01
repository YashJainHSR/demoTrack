package com.tcs.internship.demotrack.jwt;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tcs.internship.demotrack.jwt.resource.AuthenticationException;
import com.tcs.internship.demotrack.jwt.resource.JwtAuthenticationRestController;
import com.tcs.internship.demotrack.jwt.resource.JwtTokenRequest;
import com.tcs.internship.demotrack.model.UserCredentials;
import com.tcs.internship.demotrack.repository.UserCredentialsRepository;
import org.springframework.security.core.Authentication;

@RunWith(MockitoJUnitRunner.class)
public class JwtControllerTest {

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	JwtTokenUtil jwtTokenUtil;

	@Mock
	UserDetailsService jwtInMemoryUserDetailsService;

	@Mock
	HttpServletRequest request;

	@InjectMocks
	JwtAuthenticationRestController jwtAuthenticationRestController;

	@BeforeClass
	public static void setHeader() {
		JwtAuthenticationRestController.setTokenHeader("Authorization");
	}

	@Test
	public void createAuthenticationTokenTest() {
		JwtAuthenticationRestController spyJwtAuthenticationRestController = Mockito
				.spy(jwtAuthenticationRestController);
		Mockito.doNothing().when(spyJwtAuthenticationRestController).authenticate(Mockito.any(), Mockito.any());
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		when(jwtInMemoryUserDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(findFirst.get());
		UserCredentials userCredentials = new UserCredentials((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Yash Jain", "yashjain0898@gmail.com",
				"Admin", "");
		when(userCredentialsRepository.findByUsername("1838900")).thenReturn(userCredentials);
		ResponseEntity<?> r = new ResponseEntity<>(userCredentials, HttpStatus.OK);
		assertEquals(r, spyJwtAuthenticationRestController
				.createAuthenticationToken(new JwtTokenRequest("1838900", "EHzocMRbwMEA54vyB9GZPg")));
	}

	@Test
	public void refreshAndGetAuthenticationTokenTest1() {
		when(request.getHeader("Authorization")).thenReturn("tokenwithfulllength");
		when(jwtTokenUtil.canTokenBeRefreshed("thfulllength")).thenReturn(true);
		when(jwtTokenUtil.refreshToken("thfulllength")).thenReturn("refreshedtokenwithfulllength");
		ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.OK);
		assertEquals(r.getStatusCode(),
				jwtAuthenticationRestController.refreshAndGetAuthenticationToken(request).getStatusCode());
	}

	@Test
	public void refreshAndGetAuthenticationTokenTest2() {
		when(request.getHeader("Authorization")).thenReturn("tokenwithfulllength");
		when(jwtTokenUtil.canTokenBeRefreshed("thfulllength")).thenReturn(false);
		ResponseEntity<?> r = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		assertEquals(r.getStatusCode(),
				jwtAuthenticationRestController.refreshAndGetAuthenticationToken(request).getStatusCode());
	}

	@Test
	public void handleAuthenticationExceptionTest() throws IOException {
		ResponseEntity<?> r = jwtAuthenticationRestController
				.handleAuthenticationException(new AuthenticationException("Check", new Exception()));
		assertEquals(r.getBody(), "Check");
		assertEquals(r.getStatusCode(), HttpStatus.FORBIDDEN);
	}

	@Test
	public void authenticateTest1() {
		Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password")))
				.thenReturn(authentication);
		jwtAuthenticationRestController.authenticate("username", "password");
	}

	@Test(expected = AuthenticationException.class)
	public void authenticateTest2() {
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password")))
				.thenThrow(DisabledException.class);
		jwtAuthenticationRestController.authenticate("username", "password");
	}

	@Test(expected = AuthenticationException.class)
	public void authenticateTest3() {
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password")))
				.thenThrow(BadCredentialsException.class);
		jwtAuthenticationRestController.authenticate("username", "password");
	}

}
