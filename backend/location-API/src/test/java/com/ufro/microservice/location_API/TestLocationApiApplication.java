package com.ufro.microservice.location_API;

import org.springframework.boot.SpringApplication;

public class TestLocationApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(LocationApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
