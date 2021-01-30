package com.example.demo.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeTest;
import com.example.demo.repository.EmployeeRepository;

public class EmployeeControllerTest {

	@Mock
	EmployeeRepository employeeRepository;
	
	@InjectMocks
	EmployeeController employeeController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void all_test() {
		 when(employeeRepository.findAll()).thenReturn(createEmployeeMock());
		 assertNotNull(employeeController.all());
	}

	@Test
	public void one_ok () {
		when(employeeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(EmployeeTest.create()));
		EntityModel<Employee> employee = employeeController.one(1L);
		assertEquals("Bilbo Baggins", employee.getContent().getName());
		assertEquals("burglar", employee.getContent().getRole());
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void one_NotOk () {
		employeeController.one(2L);
	}
	
	@Test
	public void newEmployee_test() {
		Employee employee = EmployeeTest.create();
		when(employeeRepository.save(employee)).thenReturn(employee);
		Employee newEmployee = employeeController.newEmployee(employee);
		assertEquals(employee.getName(), newEmployee.getName());
		assertEquals(employee.getRole(), newEmployee.getRole());
	}
	
	@Test
	public void replaceEmployee() {
		Employee employee = EmployeeTest.create();
		when(employeeRepository.save(employee)).thenReturn(employee);
		employee.setName("altName");
		Employee employeereplace = employeeController.replaceEmployee(employee, 1L);
		assertEquals("altName", employeereplace.getName());
	}
	
	@Test
	public void deleteEmployee() {
		employeeController.deleteEmployee(1L);
	}
	
	private List<Employee> createEmployeeMock() {
		List<Employee> all = new ArrayList<Employee>();
		all.add(new Employee("Bilbo Baggins", "burglar"));
		all.add(new Employee("Frodo Baggins", "thief"));
		return all;
	}
}
