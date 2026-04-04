package com.ufro.microservice.route_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class RouteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteApiApplication.class, args);
	}

}
