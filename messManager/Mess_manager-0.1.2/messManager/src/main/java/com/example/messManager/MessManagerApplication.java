package com.example.messManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.messManager.Controller.HomeController;

@SpringBootApplication
public class MessManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessManagerApplication.class, args);
	}

}
