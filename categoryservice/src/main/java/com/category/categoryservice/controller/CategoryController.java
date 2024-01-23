package com.category.categoryservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.category.categoryservice.apiresponse.ApiResponse;
import com.category.categoryservice.dto.CategoryDto;
import com.category.categoryservice.entity.Category;
import com.category.categoryservice.feigncall.CategoryProductService;
import com.category.categoryservice.pojo.CategoryWiseProduct;
import com.category.categoryservice.pojo.Data;
import com.category.categoryservice.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {
	private CategoryService categoryService;
	private RestTemplate restTemplate;
	private ApiResponse apiResponse;
	private ModelMapper mapper;
	private CategoryProductService categoryProductServiceProxy;

	@Autowired
	public CategoryController(CategoryService categoryService, RestTemplate restTemplate, ModelMapper mapper,
			ApiResponse apiResponse, CategoryProductService categoryProductServiceProxy) {
		this.categoryService = categoryService;
		this.restTemplate = restTemplate;
		this.mapper = mapper;
		this.apiResponse = apiResponse;
		this.categoryProductServiceProxy = categoryProductServiceProxy;
	}

	@PostMapping("addcategory")
	public ResponseEntity<?> addCategory(@RequestBody Category category) {
		String addCategory = this.categoryService.addCategory(category);
		return new ResponseEntity<>(Map.of("status", "success", "message", addCategory), HttpStatus.OK);
	}

	@GetMapping(value = "fetchAll")
	public ResponseEntity<?> fetchAll() {
		List<Category> fetchAllCategory = this.categoryService.fetchAllCategory();
		return new ResponseEntity<>(Map.of("status", "success", "data", fetchAllCategory), HttpStatus.OK);
	}

	// openfeign calls for communicatting defferent web services
	@GetMapping("/feign")
	public ResponseEntity<?> getAllProduct() {
		return this.categoryProductServiceProxy.getProduct();
	}

	@GetMapping("/feign/{id}")
	public ResponseEntity<?> getSingleProduct(@PathVariable Integer id) {
		return this.categoryProductServiceProxy.getSingleProductData(id);
	}
	
	
	@GetMapping("/feign/productfromcategoryid/{category_id}")
	public ResponseEntity<?> findProductByCategoryId(@PathVariable("category_id") Integer category_id){
		return this.categoryProductServiceProxy.getProductBasedOnCategoryId(category_id);
		
	}
	
	

	// fetch product from category
	@GetMapping(value = "fetchBycategory/{cid}")
	@CircuitBreaker(name = "orderProductBreaker", fallbackMethod = "orderProductBreaker")
	@Retry(name = "categoryProductService", fallbackMethod = "categoryProductService")
	public ResponseEntity<?> fetchProductFromCategory(@PathVariable("cid") int cid)
			throws JsonMappingException, JsonProcessingException {
		// ********************FIRST WAY********************
		// ResponseEntity forObject =
		// this.restTemplate.getForObject("http://localhost:8085/api/product/findProductByCategoryId/"+id+"",
		// ResponseEntity.class);
		// ********************FIRST WAY********************
		ArrayList<?> arrayResponse = this.restTemplate
				.getForObject("http://PRODUCT/api/product/findProductByCategoryId/" + cid + "", ArrayList.class);
		// ObjectMapper mapper = new ObjectMapper();
///	 JsonNode rootNode = mapper.readValues(forObject, JsonNode.class);
		// JsonNode uri = rootNode.get("uri");
		// ********************SECOND WAY********************
		ResponseEntity<CategoryWiseProduct> result = this.restTemplate.getForEntity(
				"http://PRODUCT/api/product/findProductByCategoryId/" + cid + "", CategoryWiseProduct.class);
		// "data", RestTemplate,
		// "data", data,
		ArrayList<Data> data = result.getBody().data;
		return new ResponseEntity<>(Map.of("status", "success", "data", data), HttpStatus.OK);
	}

	// handle after dependent service is down any reason
	public ResponseEntity<?> orderProductBreaker(int cid, Exception ex) {
		return new ResponseEntity<>(
				Map.of("status", "200", "message",
						"Down Product Service if Service Is Up And Down Your Request Is Send Back Response Normally !"),
				HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> categoryProductService(int cid, Exception ex) {
		return new ResponseEntity<>(Map.of("status", "200", "message",
				"Retry Product Service if Service Is Up And Down Your Request Is Send Back Response Normally !"),
				HttpStatus.BAD_REQUEST);
	}

	@GetMapping("remove/{cid}")
	public ResponseEntity<?> removeData(@PathVariable("cid") int cid) {
		try {
			String deleteCategory = this.categoryService.deleteCategory(cid);
			return this.apiResponse.sendResponse(deleteCategory, "OK", HttpStatus.OK);
		} catch (Exception e) {
			return this.apiResponse.sendResponse(null, "INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// fetch category from category id
	@GetMapping("/fetchById/{cid}")
	@CircuitBreaker(name = "orderProductBreaker", fallbackMethod = "orderProductBreaker")
	public ResponseEntity<?> findCategoryFromCategoryId(@PathVariable("cid") int cid) {
		Optional<Category> findCategoryById = this.categoryService.findCategoryById(cid);
		CategoryDto map = this.mapper.map(findCategoryById.get(), CategoryDto.class);
		ResponseEntity<CategoryWiseProduct> forEntity = this.restTemplate.getForEntity(
				"http://PRODUCT/api/product/findProductByCategoryId/" + cid + "", CategoryWiseProduct.class);
		ArrayList<Data> data = forEntity.getBody().data;
		List<Data> list = data.stream().map(curObj -> {
			Data s = new Data();
			s.setProductname(curObj.productname);
			s.setCategoryid(curObj.getCategoryid());
			s.setPid(curObj.getPid());
			s.setCreateddate(curObj.getCreateddate());
			return s;
		}).toList();
		// categoryDto.setData(data);
		map.setData(data);
		return apiResponse.sendResponse(map, "categorywisedata", HttpStatus.OK);
		// return new ResponseEntity<>(Map.of("data", map), );

		// return new
		// ResponseEntity<>(Map.of("data",findCategoryById,"product",forEntity),HttpStatus.OK);
	}

}
