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
import com.example.demo.model.Manager;
import com.example.demo.model.User;
import com.example.demo.service.ManagerService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@PostMapping("/add")
	public Manager postManager(@RequestBody Manager manager) {
		User user = manager.getUser();
		user.setRole("MANAGER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.add(user);
        manager.setUser(user);
		return managerService.add(manager);
	}
	
	@GetMapping("/all")
	public List<Manager> getAll() {
		return managerService.getAll();
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(managerService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody Manager manager) {
		try {
			managerService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		manager.setId(id);
		return ResponseEntity.status(HttpStatus.OK).body(managerService.add(manager));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			managerService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		managerService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
