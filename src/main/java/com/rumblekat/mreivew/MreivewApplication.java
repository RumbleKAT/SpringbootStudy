package com.rumblekat.mreivew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MreivewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MreivewApplication.class, args);
    }

}
