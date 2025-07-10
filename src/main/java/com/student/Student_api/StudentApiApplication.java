package com.student.Student_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StudentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApiApplication.class, args);
	}

}
