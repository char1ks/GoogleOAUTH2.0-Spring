package com.example.SpringRedis_pet2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringRedisPet2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisPet2Application.class, args);
	}

}
