package com.mats.overload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OverloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(OverloadApplication.class, args);
	}

}
