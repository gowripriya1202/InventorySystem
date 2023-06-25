package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.persistence.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Product add(Product product) {
		return productRepository.save(product);
	}

	public List<Product> getAll() {
		return productRepository.findAll();
	}

	public Product getById(int id) throws ResourceNotFoundException {
		Optional<Product> optional = productRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return optional.get();
	}
	
	public void delete(int id) {
		productRepository.deleteById(id);
	}



}
