package com.vanhackathon.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1093881347009882888L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String arg0, Exception arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UserNotFoundException(String arg0, Exception arg1) {
		super(arg0, arg1);
	}

	public UserNotFoundException(String arg0) {
		super(arg0);
	}

	public UserNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
