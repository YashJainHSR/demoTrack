package com.tcs.internship.demotrack.jwt;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tcs.internship.demotrack.model.UserCredentials;
import com.tcs.internship.demotrack.repository.UserCredentialsRepository;

@RunWith(MockitoJUnitRunner.class)
public class JwtInMemoryUserDetailsServiceTest {

	@Mock
	List<JwtUserDetails> UserList;

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@InjectMocks
	JwtInMemoryUserDetailsService jwtInMemoryUserDetailsService;

	@Test
	public void loadUserByUsernameTest1() {
		when(userCredentialsRepository.findByUsername("1838900")).thenReturn(new UserCredentials((long) 1, "1838900",
				"EHzocMRbwMEA54vyB9GZPg", "Yash Jain", "yashjain0898@gmail.com", "Sales", ""));
		assertEquals("1838900", jwtInMemoryUserDetailsService.loadUserByUsername("1838900").getUsername());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsernameTest2() {
		when(userCredentialsRepository.findByUsername("1838900")).thenReturn(null);
		jwtInMemoryUserDetailsService.loadUserByUsername("1838900");
	}

}
