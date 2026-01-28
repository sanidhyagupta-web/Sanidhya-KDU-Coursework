package com.SpringAOP.SmartLockSecuritySystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class SmartLockSecuritySystemApplication{

	public static void main(String[] args) {
		SpringApplication.run(SmartLockSecuritySystemApplication.class, args);
	}

}
