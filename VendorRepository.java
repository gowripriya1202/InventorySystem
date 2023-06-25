package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer>{

}
