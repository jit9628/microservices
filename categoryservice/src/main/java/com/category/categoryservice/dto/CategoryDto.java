package com.category.categoryservice.dto;
import java.util.List;
import com.category.categoryservice.pojo.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	private int cid;
	private String categoryname;
	private List<Data> data;


}
