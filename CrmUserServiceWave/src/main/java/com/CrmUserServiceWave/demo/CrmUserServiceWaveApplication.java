package com.CrmUserServiceWave.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@EntityScan("com.CrmUserServiceWave.model")
@EnableJpaRepositories("com.CrmUserServiceWave.Repository")
@ComponentScan("com.CrmUserServiceWave.*")
public class CrmUserServiceWaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmUserServiceWaveApplication.class, args);
	}

}
