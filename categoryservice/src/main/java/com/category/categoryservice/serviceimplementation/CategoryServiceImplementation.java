package com.category.categoryservice.serviceimplementation;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.category.categoryservice.entity.Category;
import com.category.categoryservice.repository.CategoryRepository;
import com.category.categoryservice.service.CategoryService;
@Service
public class CategoryServiceImplementation implements CategoryService {
	private CategoryRepository categoryRepository;
	// constructor autowiring 
	@Autowired
public 	CategoryServiceImplementation(CategoryRepository categoryRepository){
		this.categoryRepository=categoryRepository;	
	}
	@Override
	public String addCategory(Category category) {
		System.out.println("name :"+category.getCategoryname());
//		check category is exists or not 
		//if(this.categoryRepository.existsByCategoryname(category.getCategoryname())) {
			//return "IsExists";
		//}
	//else {
			Category save = this.categoryRepository.save(category);
			//if(save instanceof Category) {
				return "Add";
			//}else {
				//return "Something Wrong";
			//}
	//	}
		
	}


	@Override
	public List<Category> fetchAllCategory() {
		return this.categoryRepository.findAll();
		
	}
	@Override
	public String deleteCategory(int id) {
	this.categoryRepository.deleteById(id);
		return "Delete Category";
	}
	@Override
	public Optional<Category> findCategoryById(int id) {
		Optional<Category> findById = this.categoryRepository.findById(id);
		return findById;
	}

}
