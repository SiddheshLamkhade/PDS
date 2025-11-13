package com.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
		System.out.println("Config Server has been started on port 8888....");
	}
}
