package com.softsite.models;

import com.softsite.auxiliaries.AuthorizationType;

public class Manager extends Person {

	private AuthorizationType roleLevel;
	private double salary = 1500;

	public AuthorizationType getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(AuthorizationType roleLevel) {
		this.roleLevel = roleLevel;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
