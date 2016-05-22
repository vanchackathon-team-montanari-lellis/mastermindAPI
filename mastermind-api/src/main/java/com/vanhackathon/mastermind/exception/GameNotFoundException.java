package com.vanhackathon.mastermind.exception;

public class GameNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -7766495001150359833L;

	public GameNotFoundException(String message) {
		super(message);
	}
}
