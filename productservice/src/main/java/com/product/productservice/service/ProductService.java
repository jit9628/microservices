package com.product.productservice.service;
import java.util.List;
import java.util.Optional;

import com.product.productservice.dto.ProductDto;
import com.product.productservice.entity.Product;
public interface ProductService {
	public Boolean addProduct(ProductDto productDto) ;
	public List<Product> fetchAllProduct();
	public Optional<Product> findByProductId(int id);
	public List<Product> findProductByCategoryId(int categoryid);
	public String deleteProduct(int id);
}
