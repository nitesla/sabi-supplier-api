package com.sabisupplier.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.sabi.framework")
@EntityScan(basePackages = {"com.sabisupplierscore.models"})
@EnableJpaRepositories({"com.sabisupplier.service.repositories","com.sabi.framework.repositories"})
@SpringBootApplication
public class SabiSupplierApiApplication {

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(SabiSupplierApiApplication.class, args);



	}

}
