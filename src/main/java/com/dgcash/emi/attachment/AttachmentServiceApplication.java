package com.dgcash.emi.attachment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.dgcash"})
@EnableFeignClients(basePackages = {"com.dgcash"})
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Attachment API", version = "1.0", description = "Documentation Attachment API v1.0"))
public class AttachmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachmentServiceApplication.class, args);
    }
}
