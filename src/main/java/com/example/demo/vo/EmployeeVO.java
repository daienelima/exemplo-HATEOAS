package com.example.demo.vo;

public class EmployeeVO {
	
	private Long id;
	private String name;
	private String role;
	
	public EmployeeVO() {}
	
	public EmployeeVO(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
