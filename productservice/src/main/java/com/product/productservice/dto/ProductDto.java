package com.product.productservice.dto;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private int pid;
	private String productname;
	private int quantity;
	private Date createddate;
	private int categoryid;
	private List<CategoryDto> categories;
}
