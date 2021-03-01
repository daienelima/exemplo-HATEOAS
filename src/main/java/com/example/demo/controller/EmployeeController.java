package com.example.demo.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository repository;


	@GetMapping("/employees")
	public ResponseEntity<CollectionModel<EntityModel<Employee>>> all() {

		List<EntityModel<Employee>> employees = repository.findAll().stream()
				.map(employee -> EntityModel.of(employee,
						linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel()))
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(employees));
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<EntityModel<Employee>> one(@PathVariable Long id) {

		Employee employee = repository.findById(id) 
				.orElseThrow(() -> new EmployeeNotFoundException(id));

		return ResponseEntity.of(Optional.of(EntityModel.of(employee, 
				linkTo(methodOn(EmployeeController.class).all()).withRel("employees"))));
	}

	@PostMapping("/employees")
	public ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee) {
		return ResponseEntity.ok(repository.save(newEmployee));
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		if (repository.findById(id).isPresent()) {
			return ResponseEntity.ok(this.repository.save(newEmployee));
		}
		new EmployeeNotFoundException(id);
		return ResponseEntity.noContent().build();
	}
	

	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
