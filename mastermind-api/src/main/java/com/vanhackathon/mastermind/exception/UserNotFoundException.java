package com.vanhackathon.mastermind.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -1093881347009882888L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
