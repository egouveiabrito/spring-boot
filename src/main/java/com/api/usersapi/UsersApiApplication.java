package com.api.usersapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Users API", version = "2.0", description = "Users Information"))
public class UsersApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsersApiApplication.class, args);
	}
}
