package com.tcs.internship.demotrack.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@RunWith(MockitoJUnitRunner.class)
public class JwtUnAuthorizedResponseAuthenticationEntryPointTest {
	private static final AuthenticationException AuthenticationException = new BadCredentialsException("Some Error");
	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@InjectMocks
	JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

	@Test
	public void commenceTest() throws IOException {
		jwtUnAuthorizedResponseAuthenticationEntryPoint.commence(request, response, AuthenticationException);
		Mockito.verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"You would need to provide the Jwt Token to Access This resource");
	}

}
