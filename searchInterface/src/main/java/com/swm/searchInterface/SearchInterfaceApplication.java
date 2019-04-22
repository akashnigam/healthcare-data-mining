package com.swm.searchInterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableJpaRepositories("com.swm.searchInterface")
public class SearchInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchInterfaceApplication.class, args);
	}

}