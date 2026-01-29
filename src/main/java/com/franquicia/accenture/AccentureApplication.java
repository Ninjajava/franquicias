package com.franquicia.accenture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.franquicia.accenture")
public class AccentureApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccentureApplication.class, args);
	}

}
