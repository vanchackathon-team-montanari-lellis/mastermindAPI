package com.vanhackathon.mastermind.exception;

public class NotYourTurnException extends Exception {

	private static final long serialVersionUID = -4072135846179602571L;

	public NotYourTurnException(String message) {
		super(message);
	}

}
