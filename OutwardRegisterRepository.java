package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.OutwardRegister;

public interface OutwardRegisterRepository extends JpaRepository<OutwardRegister, Integer> {

}
