package com.tcs.internship.demotrack.jwt.resource;

import java.io.Serializable;

import com.tcs.internship.demotrack.decryptor.Decrypter;

public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = -5616176897013108345L;

	private String username;
	private String password;
	private Decrypter decrypter = new Decrypter();


	public JwtTokenRequest(String username, String password) {
		super();

		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return decrypter.decrypt(this.password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
