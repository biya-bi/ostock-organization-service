package com.optimagrowth.organization;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.optimagrowth.organization.io.EnvReader;

@SpringBootApplication
@RefreshScope
@EnableMethodSecurity
@EnableFeignClients
public class OrganizationServiceApplication {

	public static void main(String[] args) throws IOException {
		EnvReader.read();
		
		SpringApplication.run(OrganizationServiceApplication.class, args);
	}

}
