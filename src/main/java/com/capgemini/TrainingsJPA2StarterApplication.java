package com.capgemini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainingsJPA2StarterApplication {

    public static void main(String[] args) {
        // Uncomment line below to use mysql database (default database name = workers_management_system, user = jpa2, pass = jpa2)
        // you can change this in application-mysql.properties

        System.setProperty("spring.profiles.active", "mysql");

        SpringApplication.run(TrainingsJPA2StarterApplication.class, args);
    }
}