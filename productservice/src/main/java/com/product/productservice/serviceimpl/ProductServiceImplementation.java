package com.product.productservice.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.productservice.dto.ProductDto;
import com.product.productservice.entity.Product;
import com.product.productservice.repositories.ProductRepository;
import com.product.productservice.service.ProductService;

@Service
public class ProductServiceImplementation implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean addProduct(ProductDto productDto) {
		System.out.println("Product Service Executed ..");
		// product is existe or not
		// this.mapper.map(productDto, Product.class)
		// this.productRepository.save(this.mapper.map(productDto, Product.class));
//		if(this.productRepository.save(this.mapper.map(productDto, Product.class)) instanceof Product)
		
		if(this.productRepository.existsByProductname(productDto.getProductname())) {
			return false;
		}
		
		Product map = this.mapper.map(productDto, Product.class);
		//map.setProductname(productDto.getProductname());
	//	map.setQuantity(productDto.getQuantity());
		map.setCreateddate(new java.util.Date());
		Product save = this.productRepository.save(map);
		return (save instanceof Product)?true:false;
//		return (this.productRepository.existsByProductname(productDto.getProductname())) ? false
//				: (this.productRepository.save(this.mapper.map(productDto, Product.class)) instanceof Product) ? true
//						: false;
		// return null;
		
	}
	@Override
	public List<Product> fetchAllProduct() {
		return this.productRepository.findAll();
	}

	@Override
	public Optional<Product> findByProductId(int id) {
		return this.productRepository.findById(id);
	//	return Optional.empty();
	}
	@Override
	public List<Product> findProductByCategoryId(int categoryid) {
	List<Product> findByCategoryid = this.productRepository.findByCategoryid(categoryid);
		return findByCategoryid;
	}
	@Override
	public String deleteProduct(int id) {
//		check if exists or not 
		// TODO Auto-generated method stub
		boolean existsById = this.productRepository.existsById(id);
		if(!existsById) {
		return "Product is not Available";	
		}
		this.productRepository.deleteById(id);
		return "product deleted";
	}
}
