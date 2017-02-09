package com.devcortes.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.devcortes" })
public class JsonParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonParserApplication.class, args);
	}
}
