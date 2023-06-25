package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.example.demo.dto.InwardRegisterDto;
import com.example.demo.dto.Supplierdto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Godown;
import com.example.demo.model.InwardRegister;
import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.service.GodownService;
import com.example.demo.service.InwardRegisterService;
import com.example.demo.service.ProductService;
import com.example.demo.service.SupplierService;

@RestController
@RequestMapping("/inwardregister")
public class InwardRegisterController {

	@Autowired
	private ProductService productService;

	@Autowired
	private GodownService godownService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private InwardRegisterService inwardRegisterService;

	@PostMapping("/add/{productId}/{godownId}/{supplierId}")
	public ResponseEntity<?> postInwardRegister(@RequestBody InwardRegister inwardRegister,
			@PathVariable("productId") int productId, @PathVariable("godownId") int godownId,
			@PathVariable("supplierId") int supplierId) {
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
		
		Supplier supplier;
		try {
			supplier = supplierService.getById(supplierId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid supplier ID given");
		}
		
		inwardRegister.setProduct(product);
		inwardRegister.setGodown(godown);
		inwardRegister.setSupplier(supplier);

		inwardRegister.setDateOfSupply(LocalDate.now());

		inwardRegister = inwardRegisterService.add(inwardRegister);
		return ResponseEntity.status(HttpStatus.OK).body(inwardRegister);
	}
	
	@GetMapping("/all")
	public List<InwardRegister> getAll() {
		return inwardRegisterService.getAll();
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(inwardRegisterService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}/{productId}/{godownId}/{supplierId}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @PathVariable("productId") int productId, @PathVariable("godownId") int godownId,
			@PathVariable("supplierId") int supplierId, @RequestBody InwardRegister inwardRegister) {
		try {
			inwardRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		inwardRegister.setId(id);
		
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
		
		Supplier supplier;
		try {
			supplier = supplierService.getById(supplierId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid supplier ID given");
		}
		
		inwardRegister.setProduct(product);
		inwardRegister.setGodown(godown);
		inwardRegister.setSupplier(supplier);

		inwardRegister.setDateOfSupply(inwardRegister.getDateOfSupply());

		inwardRegister = inwardRegisterService.add(inwardRegister);
		return ResponseEntity.status(HttpStatus.OK).body(inwardRegister);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			inwardRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		inwardRegisterService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/report")
	public List<InwardRegisterDto> inwardReport() {
		List<InwardRegister> list = inwardRegisterService.getAll();
		List<InwardRegisterDto> listDto = new ArrayList<>();
		list.stream().forEach(entry -> {
			InwardRegisterDto dto = new InwardRegisterDto();
			dto.setProductTitle(entry.getProduct().getTitle());
			dto.setProductQuantity(entry.getQuantity());
			dto.setGodownLocation(entry.getGodown().getLocation());
			dto.setGodownManager(entry.getGodown().getManager().getName());
			dto.setSupplierName(entry.getSupplier().getName());
			dto.setSupplierCity(entry.getSupplier().getCity());
			
			listDto.add(dto);
		});
		
		return listDto;
	}
	
	@GetMapping("/report/supplier/{supplierId}")
	public ResponseEntity<?> inwardReportBySupplier(@PathVariable int supplierId) {
		List<InwardRegister> list;
		try {
			list = inwardRegisterService.getBySupplierId(supplierId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		List<Supplierdto> listDto = new ArrayList<>();
		list.stream().forEach(entry -> {
			Supplierdto dto = new Supplierdto();
			dto.setProductTitle(entry.getProduct().getTitle());
			dto.setProductQuantity(entry.getQuantity());
			dto.setProductPrice(entry.getProduct().getPrice());
			dto.setSupplierName(entry.getSupplier().getName());
			dto.setSupplierCity(entry.getSupplier().getCity());
			dto.setQuantity(entry.getQuantity());
			dto.setInvoiceNumber(entry.getInvoiceNumber());
			dto.setReceiptNo(entry.getReceiptNo());
			dto.setDateOfSupply(entry.getDateOfSupply());
			listDto.add(dto);
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(listDto);
	}

}