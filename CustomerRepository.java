package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}