package com.example.demo.exception;

public class EmployeeNotFoundException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7895693172216644992L;

	public EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}
