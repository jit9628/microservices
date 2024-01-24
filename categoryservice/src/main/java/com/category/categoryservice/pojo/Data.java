package com.category.categoryservice.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
	public String pid;
	public String productname;
	public int quantity;
	public Date createddate;
	public String categoryid;

}
