package com.example.IotProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @SpringBootApplication(exclude =
// org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class IotProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotProjectApplication.class, args);
	}

}
