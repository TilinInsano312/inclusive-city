package com.ufro.microservice.location_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LocationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationApiApplication.class, args);
	}

}
