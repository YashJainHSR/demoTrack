package com.tcs.internship.demotrack.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.internship.demotrack.model.UserCredentials;
import com.tcs.internship.demotrack.repository.UserCredentialsRepository;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {
	@Autowired
	private UserCredentialsRepository userCredentialsRepository;
	static List<JwtUserDetails> UserList = new ArrayList<>();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			UserCredentials u = userCredentialsRepository.findByUsername(username);
			UserList.add(new JwtUserDetails(u.getId(), u.getUsername(), u.getPassword(), u.getRole()));
		} catch (Exception ex) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		Optional<JwtUserDetails> findFirst = UserList.stream().filter(user -> user.getUsername().equals(username))
				.findFirst();

		return findFirst.get();
	}

}
