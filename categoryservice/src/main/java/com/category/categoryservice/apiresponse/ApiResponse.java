package com.category.categoryservice.apiresponse;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
	private Object data;
	private String mesage;
	private HttpStatus status;
	public ResponseEntity<?> sendResponse(Object data,String message,HttpStatus status){
		this.data=data;
		this.mesage=message;
		this.status=status;
		return new ResponseEntity<>(Map.of("message",message,"data",data),status);
	}

}
