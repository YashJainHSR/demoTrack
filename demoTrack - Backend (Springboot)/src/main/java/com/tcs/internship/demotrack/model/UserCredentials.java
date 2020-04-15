package com.tcs.internship.demotrack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
public class UserCredentials {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank(message = "Username cannot be left blank")
	private String username;

	@NotNull
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Please follow specified requirements for Password!")
	private String password;

	@NotNull
	@NotBlank(message = "Name cannot be blank")
	private String name;

	@NotNull
	@Email(message = "Invalid Email")
	@NotBlank(message = "Email cannot be blank")
	private String email;

	@NotNull
	@NotBlank(message = "Role cannot be blank")
	private String role;

	private String token;

	public UserCredentials() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserCredentials(long id, String username, String password, String name, String email, String role,
			String token) {
		super();
		this.id = id;
		this.password = password;
		this.username = username;
		this.name = name;
		this.email = email;
		this.role = role;
		this.token = token;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserCredentials))
			return false;
		UserCredentials that = (UserCredentials) o;
		return username.equals(that.username);
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public String getToken() {
		return this.token;
	}
}
