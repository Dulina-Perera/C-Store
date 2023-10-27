package com.cstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CStore {
	public static void main(String[] args) {
		SpringApplication.run(CStore.class, args);
	}
}
