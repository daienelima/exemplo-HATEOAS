package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class EmployeeTest {
	
	@Test
	public void emplyee_new() {
		Employee employee = create();
		 assertEquals("Bilbo Baggins", employee.getName());
		 assertEquals("burglar", employee.getRole());
		 assertEquals(1L, employee.getId());
	}
	
	public static Employee create() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Bilbo Baggins");
		employee.setRole("burglar");
		return employee;
	}

}
