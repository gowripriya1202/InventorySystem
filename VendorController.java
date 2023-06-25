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
import com.example.demo.model.Vendor;
import com.example.demo.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	private VendorService vendorService; 
	
	@PostMapping("/add")
    public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
        Vendor savedVendor = vendorService.insert(vendor);
        return ResponseEntity.status(HttpStatus.OK).body(savedVendor);
    }

    @GetMapping("/all")
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable("id") int id) throws ResourceNotFoundException {
        Vendor vendor = vendorService.getVendor(id);
        if (vendor == null) {
            throw new ResourceNotFoundException("Vendor not found with ID: " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(vendor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable("id") int id) throws ResourceNotFoundException {
        Vendor vendor = vendorService.getVendor(id);
        if (vendor == null) {
            throw new ResourceNotFoundException("Vendor not found with ID: " + id);
        }
        vendorService.deleteVendor(vendor);
        return ResponseEntity.status(HttpStatus.OK).body("Vendor deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable("id") int id, @RequestBody Vendor updatedVendor) throws ResourceNotFoundException {
        Vendor vendor = vendorService.getVendor(id);
        if (vendor == null) {
            throw new ResourceNotFoundException("Vendor not found with ID: " + id);
        }
        updatedVendor.setId(id);
        Vendor savedVendor = vendorService.insert(updatedVendor);
        return ResponseEntity.ok(savedVendor);
    }
}
