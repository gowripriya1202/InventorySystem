package com.example.demo.controller;

import java.time.LocalDate;
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
import com.example.demo.model.CustomerProduct;
import com.example.demo.model.Product;
import com.example.demo.service.CustomerProductService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.InwardRegisterService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/customer-product")
public class CustomerProductController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerProductService customerProductService;
	
	@Autowired
	private InwardRegisterService inwardRegisterService;

	@PostMapping("/purchase/{customerId}/{productId}")
	public ResponseEntity<?> purchaseApi(@RequestBody CustomerProduct customerProduct,
			@PathVariable("customerId") int customerId, @PathVariable("productId") int productId) {
		Customer customer;
		try {
			customer = customerService.getById(customerId);
		} catch (ResourceNotFoundException e) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid customer ID given");
		}

		Product product;
		try {
			product = productService.getById(productId);
		} catch (ResourceNotFoundException e) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID given");
		}

		customerProduct.setCustomer(customer);
		customerProduct.setProduct(product);
		customerProduct.setDateOfPurchase(LocalDate.now());

		boolean status = inwardRegisterService.checkQuantity(productId, customerProduct.getQuantityPurchased());
		if (status == false)
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product out of stock");

		customerProduct = customerProductService.add(customerProduct);
		return ResponseEntity.status(HttpStatus.OK).body(customerProduct);
	}
	
	@GetMapping("/all")
	public List<CustomerProduct> getAll() {
		return customerProductService.getAll();
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(customerProductService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}/{customerId}/{productId}")
	public ResponseEntity<?> update(@PathVariable int id, @PathVariable("customerId") int customerId, @PathVariable("productId") int productId, @RequestBody CustomerProduct customerProduct) {
		try {
			customerProductService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		customerProduct.setId(id);
		Customer customer;
		try {
			customer = customerService.getById(customerId);
		} catch (ResourceNotFoundException e) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid customer ID given");
		}

		Product product;
		try {
			product = productService.getById(productId);
		} catch (ResourceNotFoundException e) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID given");
		}

		customerProduct.setCustomer(customer);
		customerProduct.setProduct(product);
		customerProduct.setDateOfPurchase(customerProduct.getDateOfPurchase());

		boolean status = inwardRegisterService.checkQuantity(productId, customerProduct.getQuantityPurchased());
		if (status == false)
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product out of stock");

		customerProduct = customerProductService.add(customerProduct);
		return ResponseEntity.status(HttpStatus.OK).body(customerProduct);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			customerProductService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		customerProductService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}