package com.test.emi.attachment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.test"})
public class AttachmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachmentServiceApplication.class, args);
    }
}
