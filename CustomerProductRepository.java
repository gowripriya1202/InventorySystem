package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CustomerProduct;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Integer>{

	@Query("select cusprod from CustomerProduct cusprod where cusprod.customer.id=?1")
	List<CustomerProduct> findAllByCustomerId(int customerId);
	
}