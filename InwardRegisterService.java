package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.InwardRegister;
import com.example.demo.persistence.InwardRegisterRepository;

@Service
public class InwardRegisterService {

	@Autowired
	private InwardRegisterRepository inwardRegisterRepository;
	
	public InwardRegister add(InwardRegister inwardRegister) {
		 
		return inwardRegisterRepository.save(inwardRegister);
	}

	public boolean checkQuantity(int productId, int quantityPuchased) {
		InwardRegister inwardRegister = inwardRegisterRepository
				.checkQuantity(productId,quantityPuchased);
		if(inwardRegister == null)
			return false;
		return true;
	}

	public List<InwardRegister> getAll() {
		
		return inwardRegisterRepository.findAll();
	}

	

	public InwardRegister getById(int id) throws ResourceNotFoundException {
		Optional<InwardRegister> optional = inwardRegisterRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return optional.get();
	}
	
	public List<InwardRegister> getBySupplierId(int supplierId) throws ResourceNotFoundException {
		List<InwardRegister> inwardRegisterList = inwardRegisterRepository.findAllBySupplierId(supplierId);
		
		if (inwardRegisterList == null) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return inwardRegisterList;
	}
	
	public void delete(int id) {
		inwardRegisterRepository.deleteById(id);
	}
	
}