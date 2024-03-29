package com.category.categoryservice.feigncall;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.category.categoryservice.feignfallbacks.FallBacks;

@FeignClient(value = "PRODUCT",path = "/api/product/",fallback = FallBacks.class)
public interface CategoryProductService {
	@GetMapping("fetchAllProduct")
	public ResponseEntity<?> getProduct();
	@GetMapping("singledata/{id}")
	public ResponseEntity<?> getSingleProductData(@PathVariable(name="id") Integer id);	
	@GetMapping("/findProductByCategoryId/{category_id")
	public ResponseEntity<?> getProductBasedOnCategoryId(@PathVariable(name="category_id") Integer category_id);	
}
