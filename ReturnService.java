package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Return;
import com.example.demo.persistence.ReturnRepository;

@Service
public class ReturnService {

	@Autowired
	private ReturnRepository returnRepository;

	public Return add(Return returnRegister) {
		return returnRepository.save(returnRegister);
	}

	public List<Return> getAll() {
		return returnRepository.findAll();
	}
	
	public Return getById(int id) throws ResourceNotFoundException {
		Optional<Return> optional = returnRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return optional.get();
	}
	
	public void delete(int id) {
		returnRepository.deleteById(id);
	}
	
	public boolean checkQuantity(int productId, int quantityPuchased) {
		Return returnRegister = returnRepository.checkQuantity(productId, quantityPuchased);
		
		if(returnRegister == null) {
			return false;
		}
		
		return true;
	}
	
}
