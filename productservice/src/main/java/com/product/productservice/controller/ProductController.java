package com.product.productservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.productservice.dto.ProductDto;
import com.product.productservice.entity.Product;
import com.product.productservice.service.ProductService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

//,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE}
@RestController
@RequestMapping("/api/product/")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("saveproduct")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto)
//	@PostMapping("/saveproduct")
	{
	
		return (!this.productService.addProduct(productDto))
				? new ResponseEntity<>(Map.of("status", "CONFLICT", "message", "Can't Save Your Product"),
						org.springframework.http.HttpStatus.CONFLICT)
				: new ResponseEntity<>(Map.of("status", "OK", "message", "Saved Your Product"),
						org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("fetchAllProduct")
	// @CrossOrigin(origins = "*")
//	@LoadBalanced
	public ResponseEntity<?> findAllProduct() {
		List<Product> fetchAllProduct = this.productService.fetchAllProduct();
		return new ResponseEntity<>(Map.of("status", "OK", "data", fetchAllProduct),
				org.springframework.http.HttpStatus.OK);
	}

	@GetMapping("singledata/{id}")
	public ResponseEntity<?> getSingleProductData(@PathVariable String id) {
		Optional<Product> findByProductId = this.productService.findByProductId(id);
		return new ResponseEntity<>(Map.of("message", "success", "data", findByProductId), HttpStatus.OK);
	}

	@GetMapping("findProductByCategoryId/{categoryid}")
//	@CircuitBreaker(name = "downcategoryservice", fallbackMethod = "handleCategoryService")
	public ResponseEntity<?> findProductByCategoryId(@PathVariable("categoryid") String categoryid) {
		List<Product> findProductByCategoryId = this.productService.findProductByCategoryId(categoryid);
		return new ResponseEntity<>(Map.of("message", "success", "data", findProductByCategoryId), HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
		String deleteProduct = this.productService.deleteProduct(id);
		return new ResponseEntity<>(Map.of("status", "200", "message", deleteProduct), HttpStatus.OK);
	}

	public ResponseEntity<?> handleCategoryService(int userId, Exception ex) {
//        logger.info("Fallback is executed because service is down : ", ex.getMessage());

		ex.printStackTrace();

		// User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This
		// user is created dummy because some service is
		// down").userId("141234").build();
		return new ResponseEntity<>(Map.of("message", "success", "data", "Service Is Down"), HttpStatus.OK);
	}

}
