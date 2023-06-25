package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.Manager;
import com.example.demo.model.User;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@PostMapping("/add/{managerId}")
	public ResponseEntity<?> postEmployee(@PathVariable("managerId") int managerId, @RequestBody Employee employee) {
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Manager ID invalid");
		}
		employee.setManager(manager);
		
		User user = employee.getUser();
		user.setRole("EMPLOYEE");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.add(user);
        employee.setUser(user);
		return ResponseEntity.status(HttpStatus.OK)
				.body(employeeService.add(employee));
	}
	
	@GetMapping("/all")
	public List<Employee> getAll() {
		return employeeService.getAll();
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(employeeService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}/{managerId}")
	public ResponseEntity<?> update(@PathVariable int id, @PathVariable("managerId") int managerId, @RequestBody Employee employee) {
		try {
			employeeService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		employee.setId(id);
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Manager ID invalid");
		}
		employee.setManager(manager);
		
		User user = employee.getUser();
		user.setRole("EMPLOYEE");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.add(user);
        employee.setUser(user);
		return ResponseEntity.status(HttpStatus.OK)
				.body(employeeService.add(employee));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			employeeService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		employeeService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
