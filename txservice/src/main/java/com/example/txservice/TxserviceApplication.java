package com.example.txservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TxserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxserviceApplication.class, args);
    }

}
