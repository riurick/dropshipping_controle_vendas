package com.dropshipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication(scanBasePackages={"com.dropshipping"})
public class DropshippingApplication {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		SpringApplication.run(DropshippingApplication.class, args);
	}



}


