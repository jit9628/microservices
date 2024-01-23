package com.category.categoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
public class CategoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryserviceApplication.class, args);
	}

}
