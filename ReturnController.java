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
import com.example.demo.model.Customer;
import com.example.demo.model.Godown;
import com.example.demo.model.Manager;
import com.example.demo.model.Product;
import com.example.demo.model.Return;
import com.example.demo.service.CustomerService;
import com.example.demo.service.GodownService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReturnService;

@RestController
@RequestMapping("/returnregister")
public class ReturnController {

	@Autowired
	private ProductService productService;

	@Autowired
	private GodownService godownService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private ReturnService returnRegisterService;

	@PostMapping("/add/{productId}/{godownId}/{customerId}/{managerId}")
	public ResponseEntity<?> postInwardRegister(@RequestBody Return returnRegister,
			@PathVariable("productId") int productId, @PathVariable("godownId") int godownId,
			@PathVariable("customerId") int customerId, @PathVariable("managerId") int managerId) {
		Product product;
		try {
			product = productService.getById(productId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID given");
		}
		
		Godown godown;
		try {
			godown = godownService.getById(godownId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid godown ID given");
		}
		
		Customer customer;
		try {
			customer = customerService.getById(customerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID given");
		}
		
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid godown ID given");
		}
		
		returnRegister.setProduct(product);
		returnRegister.setGodown(godown);
		returnRegister.setReturnedBy(customer);
		returnRegister.setCheckedBy(manager);

		returnRegister = returnRegisterService.add(returnRegister);
		return ResponseEntity.status(HttpStatus.OK).body(returnRegister);
	}
	
	@GetMapping("/all")
	public List<Return> getAll() {
		return returnRegisterService.getAll();
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(returnRegisterService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}/{productId}/{godownId}/{customerId}/{managerId}")
	public ResponseEntity<?> update(@PathVariable int id, @PathVariable("productId") int productId,
			@PathVariable("godownId") int godownId, @PathVariable("customerId") int customerId,
			@PathVariable("managerId") int managerId, @RequestBody Return returnRegister) {
		try {
			returnRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		returnRegister.setId(id);
		
		Product product;
		try {
			product = productService.getById(productId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID given");
		}
		
		Godown godown;
		try {
			godown = godownService.getById(godownId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid godown ID given");
		}
		
		Customer customer;
		try {
			customer = customerService.getById(customerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID given");
		}
		
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid godown ID given");
		}
		
		returnRegister.setProduct(product);
		returnRegister.setGodown(godown);
		returnRegister.setReturnedBy(customer);
		returnRegister.setCheckedBy(manager);

		returnRegister = returnRegisterService.add(returnRegister);
		return ResponseEntity.status(HttpStatus.OK).body(returnRegister);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			returnRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		returnRegisterService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}