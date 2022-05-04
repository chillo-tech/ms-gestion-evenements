package com.cs.ge;


import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableMongock
@SpringBootApplication
public class GestionEvenementApplication {

    public static void main(final String[] args) {
        SpringApplication.run(GestionEvenementApplication.class, args);
    }

}
