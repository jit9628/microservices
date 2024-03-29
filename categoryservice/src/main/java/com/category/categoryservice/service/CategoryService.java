package com.category.categoryservice.service;

import java.util.List;
import java.util.Optional;

import com.category.categoryservice.entity.Category;

public interface CategoryService {
	public String addCategory(Category category);

	public List<Category> fetchAllCategory();

	public String deleteCategory(int id);
	public Optional<Category> findCategoryById(int id);

}
