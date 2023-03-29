package com.example.MyBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MyBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBotApplication.class, args);
	}

}
