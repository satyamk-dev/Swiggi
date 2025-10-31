package com.nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@EnableDiscoveryClient
@Tag(name = "Customer Controller", description = "APIs for managing customers")
public class SwiggiApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiggiApiGatewayApplication.class, args);
	}

}
