package com.vanhackathon.mastermind.exception;

public class InvalidColorException extends IllegalArgumentException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6742971640176577022L;

	public InvalidColorException() {
		super();
	}

	public InvalidColorException(String arg0, IllegalArgumentException arg1) {
		super(arg0, arg1);
	}

	public InvalidColorException(String arg0) {
		super(arg0);
	}

	public InvalidColorException(IllegalArgumentException arg0) {
		super(arg0);
	}

}
