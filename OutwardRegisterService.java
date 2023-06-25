package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.OutwardRegister;
import com.example.demo.persistence.OutwardRegisterRepository;

@Service
public class OutwardRegisterService {

	@Autowired
	private OutwardRegisterRepository outwardRegisterRepository;

	public OutwardRegister add(OutwardRegister outwardRegister) {
		return outwardRegisterRepository.save(outwardRegister);
	}

	public List<OutwardRegister> getAll() {
		return outwardRegisterRepository.findAll();
	}
	
	public OutwardRegister getById(int id) throws ResourceNotFoundException {
		Optional<OutwardRegister> optional = outwardRegisterRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return optional.get();
	}
	
	public void delete(int id) {
		outwardRegisterRepository.deleteById(id);
	}
	
}
