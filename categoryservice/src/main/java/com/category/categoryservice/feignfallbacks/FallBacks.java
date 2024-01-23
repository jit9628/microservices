package com.category.categoryservice.feignfallbacks;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.category.categoryservice.feigncall.CategoryProductService;

@Component
public class FallBacks implements CategoryProductService{

	@Override
	public ResponseEntity<?> getProduct() {
		return new ResponseEntity<>(
		Map.of("status", "200", "message",
				"Down Product Service if Service Is Up And Down Your Request Is Send Back Response Normally !"),
		HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getSingleProductData(Integer id) {

		return new ResponseEntity<>(
				Map.of("status", "200", "message",
						"Down Product Single Service if Service Is Up And Down Your Request Is Send Back Response Normally !"),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getProductBasedOnCategoryId(Integer category_id) {
		return new ResponseEntity<>(
				Map.of("status", "200", "message",
						"Down Product Fetch From Category Id , if Service Is Up And Down Your Request Is Send Back Response Normally !"),
				HttpStatus.BAD_REQUEST);
	}

}
