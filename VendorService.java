package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Vendor;
import com.example.demo.persistence.VendorRepository;

@Service
public class VendorService {

	@Autowired
	private VendorRepository vendorRepository; 
	
	public Vendor insert(Vendor vendor) {
		return vendorRepository.save(vendor);
	}

	public List<Vendor> getAllVendors() {
		return vendorRepository.findAll();
	}

	public Vendor getVendor(int id) {
		Optional<Vendor> optional = vendorRepository.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public Vendor getVendorAlternate(int id) throws ResourceNotFoundException {
		Optional<Vendor> optional = vendorRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("Invalid Vendor ID Given.");
		}
		return optional.get();
	}

	public void deleteVendor(Vendor vendor) {
		vendorRepository.delete(vendor);
	}

	public Vendor getById(int vendorId) {
		Optional<Vendor> optional = vendorRepository.findById(vendorId);
		if (!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}
}
