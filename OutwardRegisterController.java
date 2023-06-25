package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.example.demo.dto.OutwardRegisterdto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.CustomerProduct;
import com.example.demo.model.Godown;
import com.example.demo.model.OutwardRegister;
import com.example.demo.model.Product;
import com.example.demo.service.CustomerProductService;
import com.example.demo.service.GodownService;
import com.example.demo.service.OutwardRegisterService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/outwardregister")
public class OutwardRegisterController {

	@Autowired
	private ProductService productService;

	@Autowired
	private GodownService godownService;

	@Autowired
	private OutwardRegisterService outwardRegisterService;
	
	@Autowired
	private CustomerProductService customerProductService;

	@PostMapping("/add/{productId}/{godownId}")
	public ResponseEntity<?> postInwardRegister(@RequestBody OutwardRegister outwardRegister,
			@PathVariable("productId") int productId, @PathVariable("godownId") int godownId) {
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
		
		outwardRegister.setProduct(product);
		outwardRegister.setGodown(godown);

		outwardRegister.setDateOfDelivery(LocalDate.now());

		outwardRegister = outwardRegisterService.add(outwardRegister);
		return ResponseEntity.status(HttpStatus.OK).body(outwardRegister);
	}
	
	@GetMapping("/all")
	public List<OutwardRegister> getAll() {
		return outwardRegisterService.getAll();
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(outwardRegisterService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}/{productId}/{godownId}")
	public ResponseEntity<?> update(@PathVariable int id, @PathVariable("productId") int productId, @PathVariable("godownId") int godownId, @RequestBody OutwardRegister outwardRegister) {
		try {
			outwardRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		outwardRegister.setId(id);
		
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
		
		outwardRegister.setProduct(product);
		outwardRegister.setGodown(godown);

		outwardRegister.setDateOfDelivery(outwardRegister.getDateOfDelivery());

		outwardRegister = outwardRegisterService.add(outwardRegister);
		return ResponseEntity.status(HttpStatus.OK).body(outwardRegister);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			outwardRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		outwardRegisterService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/report")
	public List<OutwardRegisterdto> outwardReport() {
		List<OutwardRegister> list = outwardRegisterService.getAll();
		List<OutwardRegisterdto> listDto = new ArrayList<>();
		list.stream().forEach(entry -> {
			OutwardRegisterdto dto = new OutwardRegisterdto();
			dto.setProductTitle(entry.getProduct().getTitle());
			dto.setProductQuantity(entry.getQuantity());
			dto.setGodownLocation(entry.getGodown().getLocation());
			dto.setGodownManager(entry.getGodown().getManager().getName());
			dto.setQuantity(entry.getQuantity());
			dto.setInvoiceNumber(entry.getInvoice());
			dto.setPurpose(entry.getPurpose());
			dto.setReceiptNo(entry.getRecieptNo());
			dto.setBillValue(entry.getBillValue());
			dto.setDeliveredTo(entry.getDeliveredTo());
			dto.setDateOfDelivery(entry.getDateOfDelivery());
			listDto.add(dto);
		});
		
		return listDto;
	}
	
	@GetMapping("/report/customer/{customerId}")
	public ResponseEntity<?> outwardReportByCustomer(@PathVariable int customerId) {
		List<CustomerProduct> list;
		try {
			list = customerProductService.getByCustomerId(customerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid customer ID given");
		}
		
		HashMap<String, Integer> map = new HashMap<>();
		list.stream().forEach(entry -> {
			if (map.containsKey(entry.getProduct().getTitle())) {
				map.put(entry.getProduct().getTitle(), map.get(entry.getProduct().getTitle()) + entry.getQuantityPurchased());
			}
			else {
				map.put(entry.getProduct().getTitle(), entry.getQuantityPurchased());				
			}
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}