package com.LibraryManagement2.ProductionReadyLib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductionReadyLibApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductionReadyLibApplication.class, args);
    }
}