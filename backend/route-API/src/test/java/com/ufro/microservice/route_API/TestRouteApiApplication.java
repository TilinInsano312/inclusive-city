package com.ufro.microservice.route_API;

import org.springframework.boot.SpringApplication;

public class TestRouteApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(RouteApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
