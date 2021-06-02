package com.psy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final String APP_NAME = Application.class.getSimpleName();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}