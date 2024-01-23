package com.product.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.productservice.entity.Product;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	boolean existsByProductname(String productname); 
	List<Product> findByCategoryid(int categoryid);
	 boolean existsById(Integer id);	
}
