package com.ll.exam.app_2022_09_22;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableBatchProcessing
@EnableJpaAuditing
public class App20220922Application {

    public static void main(String[] args) {
        SpringApplication.run(App20220922Application.class, args);
    }

}
