package com.tcs.internship.demotrack.jwt.resource;

public class AuthenticationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4056582073603473746L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
