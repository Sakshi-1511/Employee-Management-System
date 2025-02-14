package com.springboot.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.backend.exception.ResourceNotFoundException;
import com.springboot.backend.model.Employee;
import com.springboot.backend.repository.EmployeeRepository;
import com.springboot.backend.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> employee=employeeRepository.findById(id);
		if(employee.isPresent()) {
			return employee.get();
			
			
		}
		else {
			throw new ResourceNotFoundException("Employee","Id",id);
		}
	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {
		//we need to check wheather a employee with given id is exist in databaseor not
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee","Id",id));
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		
		//save existing employee to db
		employeeRepository.save(existingEmployee);
		
		return existingEmployee;
	}

	@Override
	public void  deleteEmployee(long id) {
		
		////we need to check wheather a employee with given id is exist in databaseor not
		employeeRepository.findById(id).orElseThrow(	() -> new ResourceNotFoundException("Employee","Id",id));
		
		employeeRepository.deleteById(id);
	}

}
