package com.pavan.room_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Objects;


@SpringBootApplication
@EnableDiscoveryClient
public class RoomServiceApplication {


	public static void main(String[] args) {

		Dotenv dotenv=Dotenv.load();
		System.setProperty("MYSQL_USERNAME", Objects.requireNonNull(dotenv.get("MYSQL_USERNAME")));
		System.setProperty("MYSQL_PASSWORD", Objects.requireNonNull(dotenv.get("MYSQL_PASSWORD")));



		SpringApplication.run(RoomServiceApplication.class, args);
	}

}
