package com.category.categoryservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	  public int pid;
	    public String productname;
	    public int quantity;
	    public Date createddate;
	    public int categoryid;
}
