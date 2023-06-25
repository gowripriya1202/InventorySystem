package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer>{

}
