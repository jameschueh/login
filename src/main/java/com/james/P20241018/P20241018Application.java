package com.james.P20241018;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.james.P20241018.filter.AuthFilter;

@SpringBootApplication
public class P20241018Application {

	public static void main(String[] args) {
		SpringApplication.run(P20241018Application.class, args);
	}
}
