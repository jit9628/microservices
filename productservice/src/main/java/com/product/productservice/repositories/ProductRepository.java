package com.product.productservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.productservice.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	boolean existsByProductname(String productname); 
	List<Product> findByCategoryid(String categoryid);
	 boolean existsById(String id);	
}
