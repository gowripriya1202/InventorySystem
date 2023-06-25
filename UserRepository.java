package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("select u from User u where u.username=?1")
	User getUserByUsername(String username);

}
