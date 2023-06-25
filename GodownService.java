package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Godown;
import com.example.demo.persistence.GodownRepository;

@Service
public class GodownService {

	@Autowired
	private GodownRepository godownRepository;

	public Godown add(Godown godown) {
		return godownRepository.save(godown);
	}
	
	public List<Godown> getAll() {
		return godownRepository.findAll();
	}

	public Godown getById(int id) throws ResourceNotFoundException {
		Optional<Godown> optional = godownRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return optional.get();
	}
	
	public void delete(int id) {
		godownRepository.deleteById(id);
	}
}
