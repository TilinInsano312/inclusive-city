package com.ufro.microservice.account_API;

import org.springframework.boot.SpringApplication;

public class TestAccountApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(AccountApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
