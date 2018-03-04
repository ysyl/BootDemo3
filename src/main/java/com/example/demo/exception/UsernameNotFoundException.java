package com.example.demo.exception;

public class UsernameNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3106548410978992267L;

	public UsernameNotFoundException(String couse)
	{
		super(couse);
	}
}
