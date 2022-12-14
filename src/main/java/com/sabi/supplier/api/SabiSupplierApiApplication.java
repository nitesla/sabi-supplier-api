package com.sabi.supplier.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = "com.sabi.framework")
@EntityScan(basePackages = {"com.sabisupplierscore.models"})
@EnableJpaRepositories({"com.sabi.supplier.service.repositories","com.sabi.framework.repositories"})
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
public class SabiSupplierApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SabiSupplierApiApplication.class, args);



	}

}
