package com.quetzalcoatl.otstesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class OtsTestTaskApplication {

	public static void main(String[] args) {
        ApiContextInitializer.init();
		SpringApplication.run(OtsTestTaskApplication.class, args);

	}

}
