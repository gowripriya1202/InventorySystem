package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Godown;
import com.example.demo.model.Manager;
import com.example.demo.service.GodownService;
import com.example.demo.service.ManagerService;

@RestController
@RequestMapping("/godown")
public class GodownController {

	@Autowired
	private GodownService godownService;

	@Autowired
	private ManagerService managerService;

	@PostMapping("/add/{managerId}")
	public ResponseEntity<?> insertGodown(@PathVariable("managerId") int managerId, @RequestBody Godown godown) {
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Manager ID");
		}

		godown.setManager(manager);

		godown = godownService.add(godown);

		return ResponseEntity.status(HttpStatus.OK).body(godown);
	}

	@GetMapping("/all")
	public List<Godown> getAll() {
		return godownService.getAll();
	}

	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(godownService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/update/{id}/{managerId}")
	public ResponseEntity<?> update(@PathVariable int id, @PathVariable("managerId") int managerId,
			@RequestBody Godown godown) {
		try {
			godownService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

		godown.setId(id);
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Manager ID");
		}

		godown.setManager(manager);

		godown = godownService.add(godown);

		return ResponseEntity.status(HttpStatus.OK).body(godown);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			godownService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

		godownService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/report")
	public List<Godown> godownReport() {
		return godownService.getAll();
	}

}
