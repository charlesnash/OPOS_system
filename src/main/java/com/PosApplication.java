package com;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosApplication  {

	public static void main(String[] args) {
		SpringApplication.run(PosApplication.class, args);

        System.out.println(System.getProperty("os.arch"));
        System.out.println("hello");
        System.out.println("world");
    }



}
