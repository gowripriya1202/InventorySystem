package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Godown;

public interface GodownRepository extends JpaRepository<Godown, Integer>{

}