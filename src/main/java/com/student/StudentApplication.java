package com.student;

import com.student.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentApplication extends AppConfig {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

}
