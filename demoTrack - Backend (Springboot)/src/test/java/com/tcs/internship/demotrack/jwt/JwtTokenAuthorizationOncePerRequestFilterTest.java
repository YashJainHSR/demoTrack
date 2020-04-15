package com.tcs.internship.demotrack.jwt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import io.jsonwebtoken.ExpiredJwtException;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenAuthorizationOncePerRequestFilterTest {

	@Mock
	UserDetailsService jwtInMemoryUserDetailsService;

	@Mock
	JwtTokenUtil jwtTokenUtil;
	
	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;
	
	@Mock
	FilterChain chain;
	
	@InjectMocks
	JwtTokenAuthorizationOncePerRequestFilter jwtTokenAuthorizationOncePerRequestFilter;
	
	

	@Test
	public void doFilterInternalTest1() throws ServletException, IOException {
		JwtTokenAuthorizationOncePerRequestFilter spyJwtTokenAuthorizationOncePerRequestFilter = Mockito
				.spy(jwtTokenAuthorizationOncePerRequestFilter);
		Mockito.doReturn("Authorization").when(spyJwtTokenAuthorizationOncePerRequestFilter).getTokenHeader();
		JwtTokenUtil jwtTokenUtilNew = new JwtTokenUtil("mySecret",(long)604800);
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = "Bearer " + jwtTokenUtilNew.generateToken(userDetails);
		when(request.getRequestURL()).thenReturn(new StringBuffer("Testing"));
		when(request.getHeader(Mockito.anyString())).thenReturn(token);
		when(jwtInMemoryUserDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
		when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenReturn("1838900");
		assertDoesNotThrow(() -> {spyJwtTokenAuthorizationOncePerRequestFilter.doFilterInternal(request, response, chain);});
	}
	
	@Test
	public void doFilterInternalTest6() throws ServletException, IOException {
		JwtTokenAuthorizationOncePerRequestFilter spyJwtTokenAuthorizationOncePerRequestFilter = Mockito
				.spy(jwtTokenAuthorizationOncePerRequestFilter);
		Mockito.doReturn("Authorization").when(spyJwtTokenAuthorizationOncePerRequestFilter).getTokenHeader();
		JwtTokenUtil jwtTokenUtilNew = new JwtTokenUtil("mySecret",(long)604800);
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		String token = "Bearer " + jwtTokenUtilNew.generateToken(userDetails);
		when(request.getRequestURL()).thenReturn(new StringBuffer("Testing"));
		when(request.getHeader(Mockito.anyString())).thenReturn(token);
		when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenReturn("1838900");
		assertDoesNotThrow(() -> {spyJwtTokenAuthorizationOncePerRequestFilter.doFilterInternal(request, response, chain);});
	}
	
	@Test
	public void doFilterInternalTest7() throws ServletException, IOException {
		JwtTokenAuthorizationOncePerRequestFilter spyJwtTokenAuthorizationOncePerRequestFilter = Mockito
				.spy(jwtTokenAuthorizationOncePerRequestFilter);
		Mockito.doReturn("Authorization").when(spyJwtTokenAuthorizationOncePerRequestFilter).getTokenHeader();
		when(request.getRequestURL()).thenReturn(new StringBuffer("Testing"));
		JwtTokenUtil jwtTokenUtilNew = new JwtTokenUtil("mySecret",(long)604800);
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = "Bearer " + jwtTokenUtilNew.generateToken(userDetails);
		when(request.getHeader(Mockito.anyString())).thenReturn(token);
		when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenReturn("1838900");
		SecurityContextHolder.getContext().setAuthentication(null);
		when(jwtInMemoryUserDetailsService.loadUserByUsername("1838900")).thenReturn(userDetails);
		when(jwtTokenUtil.validateToken(token.substring(7), userDetails)).thenReturn(true);
		assertDoesNotThrow(() -> {spyJwtTokenAuthorizationOncePerRequestFilter.doFilterInternal(request, response, chain);});
	}
	
	@Test
	public void doFilterInternalTest2() throws ServletException, IOException {
		JwtTokenAuthorizationOncePerRequestFilter spyJwtTokenAuthorizationOncePerRequestFilter = Mockito
				.spy(jwtTokenAuthorizationOncePerRequestFilter);
		Mockito.doReturn("Authorization").when(spyJwtTokenAuthorizationOncePerRequestFilter).getTokenHeader();
		JwtTokenUtil jwtTokenUtilNew = new JwtTokenUtil("mySecret",(long)604800);
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = "Bearer " + jwtTokenUtilNew.generateToken(userDetails);
		when(request.getRequestURL()).thenReturn(new StringBuffer("Testing"));
		when(request.getHeader(Mockito.anyString())).thenReturn(token);
		when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenThrow(IllegalArgumentException.class);
		assertDoesNotThrow(() -> {spyJwtTokenAuthorizationOncePerRequestFilter.doFilterInternal(request, response, chain);});
	}
	
	@Test
	public void doFilterInternalTest3() throws ServletException, IOException {
		JwtTokenAuthorizationOncePerRequestFilter spyJwtTokenAuthorizationOncePerRequestFilter = Mockito
				.spy(jwtTokenAuthorizationOncePerRequestFilter);
		Mockito.doReturn("Authorization").when(spyJwtTokenAuthorizationOncePerRequestFilter).getTokenHeader();
		JwtTokenUtil jwtTokenUtilNew = new JwtTokenUtil("mySecret",(long)604800);
		List<JwtUserDetails> UserList = new ArrayList<>();
		UserList.add(new JwtUserDetails((long) 1, "1838900",
				"$2a$10$x6LYc5s6uGaPPmA/B6RDqOnAm.wI/By2a.Y8Fk0iGUE9kGJBr6yYu", "Admin"));
		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals("1838900"))
				.findFirst();
		UserDetails userDetails = findFirst.get();
		String token = "Bearer " + jwtTokenUtilNew.generateToken(userDetails);
		when(request.getRequestURL()).thenReturn(new StringBuffer("Testing"));
		when(request.getHeader(Mockito.anyString())).thenReturn(token);
		//when(jwtInMemoryUserDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(userDetails);
		when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString())).thenThrow(ExpiredJwtException.class);
		assertDoesNotThrow(() -> {spyJwtTokenAuthorizationOncePerRequestFilter.doFilterInternal(request, response, chain);});
	}
	
	@Test
	public void doFilterInternalTest4() throws ServletException, IOException {
		JwtTokenAuthorizationOncePerRequestFilter spyJwtTokenAuthorizationOncePerRequestFilter = Mockito
				.spy(jwtTokenAuthorizationOncePerRequestFilter);
		Mockito.doReturn("Authorization").when(spyJwtTokenAuthorizationOncePerRequestFilter).getTokenHeader();
		when(request.getRequestURL()).thenReturn(new StringBuffer("Testing"));
		when(request.getHeader(Mockito.anyString())).thenReturn("");
		assertDoesNotThrow(() -> {spyJwtTokenAuthorizationOncePerRequestFilter.doFilterInternal(request, response, chain);});
	}
	@Test
	public void doFilterInternalTest5() throws ServletException, IOException {
		JwtTokenAuthorizationOncePerRequestFilter spyJwtTokenAuthorizationOncePerRequestFilter = Mockito
				.spy(jwtTokenAuthorizationOncePerRequestFilter);
		Mockito.doReturn("Authorization").when(spyJwtTokenAuthorizationOncePerRequestFilter).getTokenHeader();
		when(request.getRequestURL()).thenReturn(new StringBuffer("Testing"));
		when(request.getHeader(Mockito.anyString())).thenReturn(null);
		assertDoesNotThrow(() -> {spyJwtTokenAuthorizationOncePerRequestFilter.doFilterInternal(request, response, chain);});
	}
	

}
