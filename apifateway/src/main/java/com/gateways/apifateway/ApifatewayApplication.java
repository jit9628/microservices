package com.gateways.apifateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApifatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApifatewayApplication.class, args);
	}
}
