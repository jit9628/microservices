package com.category.categoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.category.categoryservice.entity.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	
	boolean existsByCategoryname(String categoryname);
	

}
