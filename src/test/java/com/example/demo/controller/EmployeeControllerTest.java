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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeTest;
import com.example.demo.repository.EmployeeRepository;

//@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeController employeeController;

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
		ResponseEntity<EntityModel<Employee>> employee = employeeController.one(1L);
		assertEquals("Bilbo Baggins", employee.getBody().getContent().getName());
		assertEquals("burglar", employee.getBody().getContent().getRole());
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void one_NotOk () {
		employeeController.one(2L);
	}
	
	@Test
	public void newEmployee_test() {
		Employee employee = EmployeeTest.create();
		when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
		ResponseEntity<Employee> newEmployee = employeeController.newEmployee(employee);
		assertEquals(employee.getName(), newEmployee.getBody().getName());
		assertEquals(employee.getRole(), newEmployee.getBody().getRole());
	}
	
	@Test
	public void replaceEmployee() {
		Employee employee = EmployeeTest.create();
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		when(employeeRepository.save(employee)).thenReturn(employee);
		employee.setName("altName");
		ResponseEntity<Employee> employeereplace = employeeController.replaceEmployee(employee, 1L);
		assertEquals("altName", employeereplace.getBody().getName());
	}
	
	@Test()
	public void replaceEmployeeNotFound() {
		Employee employee = new Employee();
		assertEquals(HttpStatus.NO_CONTENT, employeeController.replaceEmployee(employee, 5L).getStatusCode());
	}
	
	@Test
	public void deleteEmployee() {
		employeeController.deleteEmployee(1L);
		Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(1L);
	}
	
	@Test
	public void spyTest() {
		List<Employee> employees = Mockito.spy(new ArrayList<Employee>());
		Employee one = new Employee("Bilbo Baggins", "burglar");
		Employee two = new Employee("Frodo Baggins", "thief");
		
		employees.add(one);
		employees.add(two);
		
		Mockito.verify(employees).add(one);
	    Mockito.verify(employees).add(two);
	    assertEquals(2, employees.size());

	    Mockito.doReturn(100).when(employees).size();
	    assertEquals(100, employees.size());
	}
	
	private List<Employee> createEmployeeMock() {
		List<Employee> all = new ArrayList<Employee>();
		all.add(new Employee("Bilbo Baggins", "burglar"));
		all.add(new Employee("Frodo Baggins", "thief"));
		return all;
	}
}
