package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Return;

public interface ReturnRepository extends JpaRepository<Return, Integer> {

	@Query("select rr from ReturnRegister rr where rr.product.id=?1 AND rr.quantity >= ?2")
	Return checkQuantity(int productId, int quantityPuchased);
	
}
