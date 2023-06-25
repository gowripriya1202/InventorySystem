package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>{

}
